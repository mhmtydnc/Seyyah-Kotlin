package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WaypointCategory
import com.example.data.model.WaypointStop
import com.example.data.remote.PredefinedRoutesData
import com.example.ui.components.ActiveCustomRouteSummaryCard
import com.example.ui.components.CategoryFilterChips
import com.example.ui.components.CustomRouteBuilderCard
import com.example.ui.components.DepartureTimePickerDialog
import com.example.ui.components.LocationPermissionSection
import com.example.ui.components.RouteSummaryBar
import com.example.ui.components.SaveTripDialog
import com.example.ui.components.StopDetailSheet
import com.example.ui.components.WaypointStopCard
import com.example.ui.viewmodel.CalculatedStopTiming
import com.example.ui.viewmodel.RouteExplorerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen(
    viewModel: RouteExplorerViewModel,
    onNavigateToLive: () -> Unit,
    onNavigateToSaved: () -> Unit,
    modifier: Modifier = Modifier
) {
    val origin by viewModel.origin.collectAsState()
    val destination by viewModel.destination.collectAsState()
    val departureHour by viewModel.departureHour.collectAsState()
    val departureMinute by viewModel.departureMinute.collectAsState()
    val selectedCategory by viewModel.selectedCategoryFilter.collectAsState()
    val routeStops by viewModel.routeStops.collectAsState()
    val isLoading by viewModel.isLoadingRoute.collectAsState()
    val favorites by viewModel.favoriteStops.collectAsState()
    val selectedStopForDetail by viewModel.selectedStopForDetail.collectAsState()

    val userLocation by viewModel.userLocation.collectAsState()
    val isFetchingLocation by viewModel.isFetchingLocation.collectAsState()
    val nearestStopMatch by viewModel.nearestStopInfo.collectAsState()

    val isCustomRouteActive by viewModel.isCustomRouteActive.collectAsState()
    val userNotificationMessage by viewModel.userNotificationMessage.collectAsState()

    val isSpeaking by viewModel.audioGuideManager.isPlaying.collectAsState()
    val speakingStopId by viewModel.audioGuideManager.currentlySpeakingStopId.collectAsState()

    var showTimePicker by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }

    val calculatedTimings = remember(routeStops, departureHour, departureMinute) {
        viewModel.calculateStopTimings()
    }

    val displayTimings = remember(calculatedTimings, selectedCategory, isCustomRouteActive) {
        val baseList = if (isCustomRouteActive) {
            calculatedTimings.filter { it.stop.isSelectedForTrip }
        } else {
            calculatedTimings
        }

        if (selectedCategory == null) baseList
        else baseList.filter { it.stop.category == selectedCategory }
    }

    val categoryCounts = remember(routeStops) {
        WaypointCategory.values().associateWith { cat ->
            routeStops.count { it.category == cat }
        }
    }

    val departureFormatted = String.format("%02d:%02d", departureHour, departureMinute)
    val totalEtaFormatted = viewModel.getTotalEstimatedArrival()
    val totalDriveHoursFormatted = viewModel.getTotalDrivingHoursAndMinutes()
    val totalDistanceKm = viewModel.getTotalDistanceKm()
    val selectedStopsCount = routeStops.count { it.isSelectedForTrip }
    val totalCandidateStopsCount = routeStops.size

    val detailSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.Explore,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Rota Rehberi",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Akıllı Yol Üstü Gezi & Rehber",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = onNavigateToSaved,
                        modifier = Modifier.testTag("open_saved_trips_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = "Kaydedilenler",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Origin & Destination Planner Box
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = origin,
                                    onValueChange = { viewModel.setOrigin(it) },
                                    label = { Text("Başlangıç Konumu") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Place,
                                            contentDescription = null,
                                            tint = Color(0xFF2E7D32)
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(
                                            onClick = { viewModel.useCurrentLocationAsOrigin() },
                                            modifier = Modifier.testTag("use_gps_origin_btn")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.MyLocation,
                                                contentDescription = "Mevcut Konumumu Kullan",
                                                tint = if (userLocation != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth().testTag("origin_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                                    )
                                )

                                OutlinedTextField(
                                    value = destination,
                                    onValueChange = { viewModel.setDestination(it) },
                                    label = { Text("Varış Konumu") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.LocationOn,
                                            contentDescription = null,
                                            tint = Color(0xFFD32F2F)
                                        )
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth().testTag("destination_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = {
                                    val temp = origin
                                    viewModel.setOrigin(destination)
                                    viewModel.setDestination(temp)
                                },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .testTag("swap_locations_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.SwapVert,
                                    contentDescription = "Konumları Değiştir",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Departure Time Picker & Live Target Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.clickable { showTimePicker = true }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AccessTime,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Kalkış: $departureFormatted",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Hedefe Varış:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = totalEtaFormatted,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // AI Analyze / Discover Route Button
                        Button(
                            onClick = { viewModel.generateRouteWithAI() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("discover_route_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Duraklar ve Varış Saatleri Hesaplanıyor...")
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.AutoAwesome,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Yol Üstü Duraklarını ve Saatleri Hesapla",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Popular Preset Suggestions
                        Text(
                            text = "Popüler Gezi Rotaları:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PredefinedRoutesData.presets.forEach { preset ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .clickable { viewModel.loadPreset(preset) }
                                        .testTag("preset_${preset.id}")
                                ) {
                                    Text(
                                        text = "${preset.origin} → ${preset.destination}",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Notification Alert Banner if any
            if (userNotificationMessage != null) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Bookmark,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = userNotificationMessage ?: "",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF1B5E20)
                                )
                            }
                            IconButton(
                                onClick = { viewModel.clearNotificationMessage() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Place,
                                    contentDescription = "Kapat",
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Location & Waypoints Matching Section (with Accompanist Permission & "Konum İzni İste" button)
            item {
                LocationPermissionSection(
                    userLocation = userLocation,
                    isFetchingLocation = isFetchingLocation,
                    nearestStopMatch = nearestStopMatch,
                    onPermissionGranted = { granted ->
                        viewModel.setPermissionGranted(granted)
                    },
                    onRequestFetchLocation = {
                        viewModel.fetchAndMatchUserLocation(setAsOrigin = false)
                    },
                    onUseAsOrigin = {
                        viewModel.useCurrentLocationAsOrigin()
                    }
                )
            }

            // Custom Route Builder OR Active Custom Route Banner
            item {
                if (isCustomRouteActive) {
                    ActiveCustomRouteSummaryCard(
                        origin = origin,
                        destination = destination,
                        departureTime = departureFormatted,
                        totalEta = totalEtaFormatted,
                        totalDistanceKm = totalDistanceKm,
                        totalTimeFormatted = totalDriveHoursFormatted,
                        selectedStopsCount = selectedStopsCount,
                        onStartNavigation = {
                            viewModel.startLiveNavigation(1)
                            onNavigateToLive()
                        },
                        onEditStops = { viewModel.switchToEditRouteMode() },
                        onNavigateToSaved = onNavigateToSaved
                    )
                } else {
                    CustomRouteBuilderCard(
                        origin = origin,
                        destination = destination,
                        departureTime = departureFormatted,
                        totalEta = totalEtaFormatted,
                        totalDistanceKm = totalDistanceKm,
                        totalTimeFormatted = totalDriveHoursFormatted,
                        selectedStopsCount = selectedStopsCount,
                        totalCandidateStopsCount = totalCandidateStopsCount,
                        onSelectPopular = { viewModel.selectTopPopularStops() },
                        onSelectAll = { viewModel.selectAllStops(true) },
                        onClearAll = { viewModel.selectAllStops(false) },
                        onCreateAndSaveRoute = {
                            if (selectedStopsCount > 0) {
                                viewModel.buildAndSaveCustomRoute()
                            } else {
                                viewModel.selectTopPopularStops()
                                viewModel.buildAndSaveCustomRoute()
                            }
                        }
                    )
                }
            }

            // Category Filter & Stops Section Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCustomRouteActive) "Rotanızdaki Duraklar (${displayTimings.size})" else "Yol Üstü Duraklar (${displayTimings.size})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        if (!isCustomRouteActive) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Tümünü Seç",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary),
                                    modifier = Modifier
                                        .clickable { viewModel.selectAllStops(true) }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                Text(
                                    text = "Kaldır",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.error),
                                    modifier = Modifier
                                        .clickable { viewModel.selectAllStops(false) }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        } else {
                            Text(
                                text = "✏️ Seçenekleri Ekle / Düzenle",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .clickable { viewModel.switchToEditRouteMode() }
                                    .padding(4.dp)
                            )
                        }
                    }

                    CategoryFilterChips(
                        selectedCategory = selectedCategory,
                        onSelectCategory = { viewModel.setCategoryFilter(it) },
                        countsMap = categoryCounts
                    )
                }
            }

            // Stops Cards
            items(displayTimings) { timing ->
                val isFav = favorites.any { it.stopId == timing.stop.id }
                val isSpeakingThis = isSpeaking && speakingStopId == timing.stop.id

                WaypointStopCard(
                    timing = timing,
                    isSpeaking = isSpeakingThis,
                    isFavorite = isFav,
                    onToggleSelect = { viewModel.toggleStopSelection(timing.stop.id) },
                    onToggleFavorite = { viewModel.toggleFavorite(timing.stop) },
                    onSpeakAudioGuide = {
                        viewModel.audioGuideManager.speak(timing.stop.id, timing.stop.audioGuideTranscript.ifBlank { timing.stop.description })
                    },
                    onClickDetail = { viewModel.selectStopForDetail(timing.stop) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    // Detail Modal Sheet
    selectedStopForDetail?.let { detailStop ->
        val currentTiming = calculatedTimings.find { it.stop.id == detailStop.id }
        val isFav = favorites.any { it.stopId == detailStop.id }
        val isSpeakingDetail = isSpeaking && speakingStopId == detailStop.id

        StopDetailSheet(
            stop = detailStop,
            timing = currentTiming,
            isSpeaking = isSpeakingDetail,
            isFavorite = isFav,
            sheetState = detailSheetState,
            onDismiss = { viewModel.selectStopForDetail(null) },
            onToggleFavorite = { viewModel.toggleFavorite(detailStop) },
            onSpeakAudioGuide = {
                viewModel.audioGuideManager.speak(detailStop.id, detailStop.audioGuideTranscript.ifBlank { detailStop.description })
            }
        )
    }

    // Save Dialog
    if (showSaveDialog) {
        SaveTripDialog(
            defaultTitle = "$origin → $destination Gezi Planı",
            onDismiss = { showSaveDialog = false },
            onSave = { title, notes ->
                viewModel.saveCurrentTrip(title, notes)
                showSaveDialog = false
            }
        )
    }

    // Departure Time Picker Dialog
    if (showTimePicker) {
        DepartureTimePickerDialog(
            currentHour = departureHour,
            currentMinute = departureMinute,
            onDismiss = { showTimePicker = false },
            onConfirm = { h, m ->
                viewModel.setDepartureTime(h, m)
                showTimePicker = false
            }
        )
    }
}
