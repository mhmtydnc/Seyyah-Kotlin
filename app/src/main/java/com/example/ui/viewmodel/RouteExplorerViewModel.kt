package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.TripDatabase
import com.example.data.local.TripRepository
import com.example.data.location.UserLocationData
import com.example.data.location.UserLocationManager
import com.example.data.model.SavedTripEntity
import com.example.data.model.WaypointCategory
import com.example.data.model.WaypointStop
import com.example.data.remote.GeminiRouteService
import com.example.data.remote.PredefinedRoutesData
import com.example.data.remote.RoadTripPreset
import com.example.data.tts.AudioGuideManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class CalculatedStopTiming(
    val stop: WaypointStop,
    val arrivalTimeFormatted: String,
    val departureTimeFormatted: String,
    val isOpenAtArrival: Boolean,
    val minutesFromDeparture: Int,
    val openHoursStatusText: String
)

data class NearestStopMatch(
    val stop: WaypointStop,
    val distanceKm: Double,
    val estimatedMinutesAway: Int
)

data class LiveTripState(
    val isNavigating: Boolean = false,
    val currentProgressKm: Int = 0,
    val currentStopIndex: Int = 0,
    val currentSpeedKmh: Int = 90,
    val simulationSpeedMultiplier: Int = 1,
    val simulatedElapsedMinutes: Int = 0,
    val activeProximityAlert: WaypointStop? = null,
    val isArrivedAtDestination: Boolean = false,
    val isGpsLiveTracking: Boolean = false,
    val lastGpsLocation: UserLocationData? = null
)

class RouteExplorerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TripRepository
    private val geminiService = GeminiRouteService()
    val audioGuideManager = AudioGuideManager(application)
    val locationManager = UserLocationManager(application)

    init {
        val db = TripDatabase.getDatabase(application)
        repository = TripRepository(db.tripDao())
    }

    val savedTrips = repository.savedTrips.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val favoriteStops = repository.favorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Current Route Planner State
    private val _origin = MutableStateFlow("İstanbul")
    val origin: StateFlow<String> = _origin.asStateFlow()

    private val _destination = MutableStateFlow("Antalya")
    val destination: StateFlow<String> = _destination.asStateFlow()

    private val _departureHour = MutableStateFlow(8)
    val departureHour: StateFlow<Int> = _departureHour.asStateFlow()

    private val _departureMinute = MutableStateFlow(30)
    val departureMinute: StateFlow<Int> = _departureMinute.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow<WaypointCategory?>(null)
    val selectedCategoryFilter: StateFlow<WaypointCategory?> = _selectedCategoryFilter.asStateFlow()

    private val _routeStops = MutableStateFlow<List<WaypointStop>>(emptyList())
    val routeStops: StateFlow<List<WaypointStop>> = _routeStops.asStateFlow()

    private val _isCustomRouteActive = MutableStateFlow(false)
    val isCustomRouteActive: StateFlow<Boolean> = _isCustomRouteActive.asStateFlow()

    private val _userNotificationMessage = MutableStateFlow<String?>(null)
    val userNotificationMessage: StateFlow<String?> = _userNotificationMessage.asStateFlow()

    private val _selectedPreset = MutableStateFlow<RoadTripPreset?>(null)
    val selectedPreset: StateFlow<RoadTripPreset?> = _selectedPreset.asStateFlow()

    private val _isLoadingRoute = MutableStateFlow(false)
    val isLoadingRoute: StateFlow<Boolean> = _isLoadingRoute.asStateFlow()

    private val _selectedStopForDetail = MutableStateFlow<WaypointStop?>(null)
    val selectedStopForDetail: StateFlow<WaypointStop?> = _selectedStopForDetail.asStateFlow()

    // Location & Waypoint Matching State
    private val _userLocation = MutableStateFlow<UserLocationData?>(null)
    val userLocation: StateFlow<UserLocationData?> = _userLocation.asStateFlow()

    private val _isFetchingLocation = MutableStateFlow(false)
    val isFetchingLocation: StateFlow<Boolean> = _isFetchingLocation.asStateFlow()

    private val _nearestStopInfo = MutableStateFlow<NearestStopMatch?>(null)
    val nearestStopInfo: StateFlow<NearestStopMatch?> = _nearestStopInfo.asStateFlow()

    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted: StateFlow<Boolean> = _isPermissionGranted.asStateFlow()

    // Live Navigation State
    private val _liveTripState = MutableStateFlow(LiveTripState())
    val liveTripState: StateFlow<LiveTripState> = _liveTripState.asStateFlow()

    private var navigationJob: Job? = null
    private var gpsTrackingJob: Job? = null

    init {
        // Load initial default preset (Istanbul -> Antalya)
        loadPreset(PredefinedRoutesData.presets[0])
    }

    fun setOrigin(newOrigin: String) {
        _origin.value = newOrigin
        _isCustomRouteActive.value = false
    }

    fun setDestination(newDest: String) {
        _destination.value = newDest
        _isCustomRouteActive.value = false
    }

    fun setDepartureTime(hour: Int, minute: Int) {
        _departureHour.value = hour.coerceIn(0, 23)
        _departureMinute.value = minute.coerceIn(0, 59)
    }

    fun setCategoryFilter(category: WaypointCategory?) {
        _selectedCategoryFilter.value = category
    }

    fun selectStopForDetail(stop: WaypointStop?) {
        _selectedStopForDetail.value = stop
    }

    fun loadPreset(preset: RoadTripPreset) {
        _selectedPreset.value = preset
        _origin.value = preset.origin
        _destination.value = preset.destination
        _isCustomRouteActive.value = false
        // Keep the preset stops available as candidates, with first 2 or 3 selected as smart default
        _routeStops.value = preset.stops.mapIndexed { index, stop ->
            stop.copy(isSelectedForTrip = index < 3)
        }
    }

    fun toggleStopSelection(stopId: String) {
        _routeStops.value = _routeStops.value.map { stop ->
            if (stop.id == stopId) {
                stop.copy(isSelectedForTrip = !stop.isSelectedForTrip)
            } else {
                stop
            }
        }
    }

    fun selectAllStops(select: Boolean) {
        _routeStops.value = _routeStops.value.map { it.copy(isSelectedForTrip = select) }
    }

    fun selectTopPopularStops() {
        val sortedByRating = _routeStops.value.sortedByDescending { it.averageRating }
        val topIds = sortedByRating.take(3).map { it.id }.toSet()
        _routeStops.value = _routeStops.value.map { stop ->
            stop.copy(isSelectedForTrip = topIds.contains(stop.id))
        }
    }

    fun switchToEditRouteMode() {
        _isCustomRouteActive.value = false
    }

    fun clearNotificationMessage() {
        _userNotificationMessage.value = null
    }

    fun buildAndSaveCustomRoute(tripTitle: String? = null, notes: String = "") {
        val selectedStops = _routeStops.value.filter { it.isSelectedForTrip }
        if (selectedStops.isEmpty()) {
            _userNotificationMessage.value = "Lütfen rotanıza en az bir durak ekleyin!"
            return
        }

        viewModelScope.launch {
            val title = if (!tripTitle.isNullOrBlank()) tripTitle else "${_origin.value} → ${_destination.value} Kişisel Gezi Rotası"
            val totalKm = getTotalDistanceKm()

            repository.saveTrip(
                SavedTripEntity(
                    title = title,
                    origin = _origin.value,
                    destination = _destination.value,
                    departureTimestamp = System.currentTimeMillis(),
                    totalDistanceKm = totalKm,
                    totalStopsCount = selectedStops.size,
                    stopsJson = selectedStops.joinToString(";") { it.name },
                    notes = notes
                )
            )

            _isCustomRouteActive.value = true
            _userNotificationMessage.value = "Rotanız başarıyla oluşturuldu ve Kaydedilen Geziler'e eklendi!"
        }
    }

    fun loadSavedTripEntity(savedTrip: SavedTripEntity) {
        _origin.value = savedTrip.origin
        _destination.value = savedTrip.destination
        val matchedPreset = PredefinedRoutesData.findPreset(savedTrip.origin, savedTrip.destination)
        val candidateStops = matchedPreset?.stops ?: geminiService.generateFallbackStops(savedTrip.origin, savedTrip.destination, 6.0)
        val savedNames = savedTrip.stopsJson.split(";").map { it.trim().lowercase() }.toSet()

        _routeStops.value = candidateStops.map { stop ->
            val isMatched = savedNames.any { it.isNotEmpty() && (stop.name.lowercase().contains(it) || it.contains(stop.name.lowercase())) }
            stop.copy(isSelectedForTrip = isMatched || savedNames.isEmpty())
        }
        _isCustomRouteActive.value = true
    }

    fun generateRouteWithAI() {
        viewModelScope.launch {
            _isLoadingRoute.value = true
            _isCustomRouteActive.value = false
            try {
                val matchedPreset = PredefinedRoutesData.findPreset(_origin.value, _destination.value)
                if (matchedPreset != null) {
                    _selectedPreset.value = matchedPreset
                    // Provide all candidate options with top 2 pre-selected for quick start
                    _routeStops.value = matchedPreset.stops.mapIndexed { index, stop ->
                        stop.copy(isSelectedForTrip = index < 3)
                    }
                } else {
                    _selectedPreset.value = null
                    val stops = geminiService.generateRouteStops(
                        origin = _origin.value,
                        destination = _destination.value,
                        routeHoursEstimate = 6.0
                    )
                    _routeStops.value = stops.mapIndexed { index, stop ->
                        stop.copy(isSelectedForTrip = index < 2)
                    }
                }
            } catch (e: Exception) {
                val fallback = geminiService.generateFallbackStops(_origin.value, _destination.value, 6.0)
                _routeStops.value = fallback.mapIndexed { index, stop ->
                    stop.copy(isSelectedForTrip = index < 2)
                }
            } finally {
                _isLoadingRoute.value = false
            }
        }
    }

    // Dynamic arrival calculation for all stops along route
    fun calculateStopTimings(): List<CalculatedStopTiming> {
        val stops = _routeStops.value
        val result = mutableListOf<CalculatedStopTiming>()

        val baseCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, _departureHour.value)
            set(Calendar.MINUTE, _departureMinute.value)
            set(Calendar.SECOND, 0)
        }

        var cumulativeMinutes = 0
        var previousDrivingMinutes = 0

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        stops.forEach { stop ->
            val legDriveMinutes = (stop.drivingMinutesFromStart - previousDrivingMinutes).coerceAtLeast(0)
            cumulativeMinutes += legDriveMinutes
            previousDrivingMinutes = stop.drivingMinutesFromStart

            val arrivalCal = (baseCal.clone() as Calendar).apply {
                add(Calendar.MINUTE, cumulativeMinutes)
            }
            val arrivalStr = timeFormat.format(arrivalCal.time)

            // If selected, add stay duration
            val stayMin = if (stop.isSelectedForTrip) stop.recommendedStayMinutes else 0
            val departureCal = (arrivalCal.clone() as Calendar).apply {
                add(Calendar.MINUTE, stayMin)
            }
            val departureStr = timeFormat.format(departureCal.time)

            // Check if open at arrival
            val isOpen = isVenueOpen(arrivalCal.get(Calendar.HOUR_OF_DAY), arrivalCal.get(Calendar.MINUTE), stop)
            val openStatusText = if (stop.alwaysOpen) {
                "7/24 Açık"
            } else if (isOpen) {
                "Varışta Açık (${stop.openTime} - ${stop.closeTime})"
            } else {
                "Varışta Kapalı (${stop.openTime} - ${stop.closeTime})"
            }

            result.add(
                CalculatedStopTiming(
                    stop = stop,
                    arrivalTimeFormatted = arrivalStr,
                    departureTimeFormatted = departureStr,
                    isOpenAtArrival = isOpen,
                    minutesFromDeparture = cumulativeMinutes,
                    openHoursStatusText = openStatusText
                )
            )

            if (stop.isSelectedForTrip) {
                cumulativeMinutes += stop.recommendedStayMinutes
            }
        }

        return result
    }

    private fun isVenueOpen(arrivalHour: Int, arrivalMin: Int, stop: WaypointStop): Boolean {
        if (stop.alwaysOpen) return true
        try {
            val openParts = stop.openTime.split(":").map { it.trim().toInt() }
            val closeParts = stop.closeTime.split(":").map { it.trim().toInt() }
            val arrivalTotalMin = arrivalHour * 60 + arrivalMin
            val openTotalMin = openParts[0] * 60 + openParts.getOrElse(1) { 0 }
            val closeTotalMin = closeParts[0] * 60 + closeParts.getOrElse(1) { 0 }

            return arrivalTotalMin in openTotalMin..closeTotalMin
        } catch (e: Exception) {
            return true
        }
    }

    fun getTotalEstimatedArrival(): String {
        val stops = _routeStops.value
        val totalDriveMin = stops.lastOrNull()?.drivingMinutesFromStart ?: 360
        val totalStayMin = stops.filter { it.isSelectedForTrip }.sumOf { it.recommendedStayMinutes }
        val totalMinutes = totalDriveMin + totalStayMin

        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, _departureHour.value)
            set(Calendar.MINUTE, _departureMinute.value)
            add(Calendar.MINUTE, totalMinutes)
        }
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(cal.time)
    }

    fun getTotalDrivingHoursAndMinutes(): String {
        val stops = _routeStops.value
        val totalDriveMin = stops.lastOrNull()?.drivingMinutesFromStart ?: 360
        val totalStayMin = stops.filter { it.isSelectedForTrip }.sumOf { it.recommendedStayMinutes }
        val totalMinutes = totalDriveMin + totalStayMin
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return "${hours}s ${mins}dk"
    }

    fun getTotalDistanceKm(): Int {
        return _routeStops.value.lastOrNull()?.kmFromStart ?: 450
    }

    fun setPermissionGranted(granted: Boolean) {
        _isPermissionGranted.value = granted
        if (granted && _userLocation.value == null) {
            fetchAndMatchUserLocation(setAsOrigin = false)
        }
    }

    fun fetchAndMatchUserLocation(setAsOrigin: Boolean = false) {
        viewModelScope.launch {
            _isFetchingLocation.value = true
            try {
                val loc = locationManager.getLastKnownLocation()
                if (loc != null) {
                    _userLocation.value = loc
                    matchStopsWithLocation(loc)
                    if (setAsOrigin) {
                        _origin.value = loc.cityName ?: "Mevcut Konum (${String.format(Locale.US, "%.2f, %.2f", loc.latitude, loc.longitude)})"
                    }
                }
            } catch (e: Exception) {
                // Ignore or handle
            } finally {
                _isFetchingLocation.value = false
            }
        }
    }

    fun matchStopsWithLocation(loc: UserLocationData) {
        val stops = _routeStops.value
        if (stops.isEmpty()) return

        var closestStop: WaypointStop? = null
        var minDistanceKm = Double.MAX_VALUE

        stops.forEach { stop ->
            val dist = UserLocationManager.calculateDistanceKm(
                loc.latitude,
                loc.longitude,
                stop.lat,
                stop.lng
            )
            if (dist < minDistanceKm) {
                minDistanceKm = dist
                closestStop = stop
            }
        }

        closestStop?.let { stop ->
            val estMinutes = ((minDistanceKm / 80.0) * 60).toInt().coerceAtLeast(1)
            _nearestStopInfo.value = NearestStopMatch(
                stop = stop,
                distanceKm = (minDistanceKm * 10).toInt() / 10.0,
                estimatedMinutesAway = estMinutes
            )
        }
    }

    fun useCurrentLocationAsOrigin() {
        val currentLoc = _userLocation.value
        if (currentLoc != null) {
            _origin.value = currentLoc.cityName ?: "Mevcut Konum (${String.format(Locale.US, "%.2f, %.2f", currentLoc.latitude, currentLoc.longitude)})"
        } else {
            fetchAndMatchUserLocation(setAsOrigin = true)
        }
    }

    // Live Navigation Companion controls
    fun startLiveNavigation(simulationMultiplier: Int = 1) {
        navigationJob?.cancel()
        gpsTrackingJob?.cancel()
        _liveTripState.value = LiveTripState(
            isNavigating = true,
            currentProgressKm = 0,
            currentStopIndex = 0,
            currentSpeedKmh = 95,
            simulationSpeedMultiplier = simulationMultiplier,
            simulatedElapsedMinutes = 0,
            activeProximityAlert = null,
            isArrivedAtDestination = false,
            isGpsLiveTracking = false
        )

        navigationJob = viewModelScope.launch {
            val totalKm = getTotalDistanceKm()
            val stops = _routeStops.value.filter { it.isSelectedForTrip }

            while (_liveTripState.value.isNavigating && _liveTripState.value.currentProgressKm < totalKm) {
                delay(1000L) // 1 second tick

                val current = _liveTripState.value
                val kmStep = (current.currentSpeedKmh.toDouble() / 3600.0 * 60.0 * current.simulationSpeedMultiplier).toInt().coerceAtLeast(1)
                val newKm = (current.currentProgressKm + kmStep).coerceAtMost(totalKm)
                val newElapsedMin = current.simulatedElapsedMinutes + (1 * current.simulationSpeedMultiplier)

                // Check proximity to upcoming stop
                var upcomingStopIndex = current.currentStopIndex
                var alertStop: WaypointStop? = null

                if (upcomingStopIndex < stops.size) {
                    val targetStop = stops[upcomingStopIndex]
                    val distLeft = targetStop.kmFromStart - newKm

                    if (distLeft in 1..25) {
                        alertStop = targetStop
                    } else if (distLeft <= 0) {
                        alertStop = targetStop
                        if (distLeft < -5) {
                            upcomingStopIndex++
                        }
                    }
                }

                val isFinished = newKm >= totalKm

                _liveTripState.value = current.copy(
                    currentProgressKm = newKm,
                    currentStopIndex = upcomingStopIndex,
                    simulatedElapsedMinutes = newElapsedMin,
                    activeProximityAlert = alertStop,
                    isArrivedAtDestination = isFinished,
                    isNavigating = !isFinished
                )

                if (isFinished) {
                    break
                }
            }
        }
    }

    fun startGpsLiveNavigation() {
        navigationJob?.cancel()
        gpsTrackingJob?.cancel()

        val totalKm = getTotalDistanceKm()
        val stops = _routeStops.value.filter { it.isSelectedForTrip }

        _liveTripState.value = LiveTripState(
            isNavigating = true,
            currentProgressKm = 0,
            currentStopIndex = 0,
            currentSpeedKmh = 0,
            simulationSpeedMultiplier = 1,
            simulatedElapsedMinutes = 0,
            activeProximityAlert = null,
            isArrivedAtDestination = false,
            isGpsLiveTracking = true
        )

        gpsTrackingJob = viewModelScope.launch {
            locationManager.getLocationUpdates(3000L).collect { loc ->
                _userLocation.value = loc
                matchStopsWithLocation(loc)

                val nearestMatch = _nearestStopInfo.value
                var alertStop: WaypointStop? = null
                if (nearestMatch != null && nearestMatch.distanceKm <= 15.0) {
                    alertStop = nearestMatch.stop
                }

                val current = _liveTripState.value
                _liveTripState.value = current.copy(
                    lastGpsLocation = loc,
                    activeProximityAlert = alertStop
                )
            }
        }
    }

    fun stopLiveNavigation() {
        navigationJob?.cancel()
        gpsTrackingJob?.cancel()
        _liveTripState.value = _liveTripState.value.copy(
            isNavigating = false,
            activeProximityAlert = null,
            isGpsLiveTracking = false
        )
        audioGuideManager.stop()
    }

    fun dismissProximityAlert() {
        _liveTripState.value = _liveTripState.value.copy(activeProximityAlert = null)
    }

    fun saveCurrentTrip(tripTitle: String, notes: String = "") {
        viewModelScope.launch {
            val title = if (tripTitle.isNotBlank()) tripTitle else "${_origin.value} → ${_destination.value} Gezisi"
            val totalKm = getTotalDistanceKm()
            val selectedStops = _routeStops.value.filter { it.isSelectedForTrip }

            repository.saveTrip(
                SavedTripEntity(
                    title = title,
                    origin = _origin.value,
                    destination = _destination.value,
                    departureTimestamp = System.currentTimeMillis(),
                    totalDistanceKm = totalKm,
                    totalStopsCount = selectedStops.size,
                    stopsJson = selectedStops.joinToString(";") { it.name },
                    notes = notes
                )
            )
        }
    }

    fun deleteTrip(tripId: Long) {
        viewModelScope.launch {
            repository.deleteTrip(tripId)
        }
    }

    fun toggleFavorite(stop: WaypointStop) {
        viewModelScope.launch {
            repository.toggleFavorite(
                stopId = stop.id,
                name = stop.name,
                city = stop.city,
                category = stop.category.name,
                rating = stop.averageRating
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioGuideManager.shutdown()
        navigationJob?.cancel()
    }
}
