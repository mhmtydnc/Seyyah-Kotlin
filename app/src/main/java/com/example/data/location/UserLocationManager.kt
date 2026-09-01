package com.example.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume

data class UserLocationData(
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float = 0f,
    val cityName: String? = null,
    val fullAddress: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

class UserLocationManager(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): UserLocationData? = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            val city = getCityNameFromCoordinates(location.latitude, location.longitude)
                            continuation.resume(
                                UserLocationData(
                                    latitude = location.latitude,
                                    longitude = location.longitude,
                                    accuracyMeters = location.accuracy,
                                    cityName = city
                                )
                            )
                        } else {
                            // If last location is null, attempt to get current location
                            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                                .addOnSuccessListener { freshLocation: Location? ->
                                    if (freshLocation != null) {
                                        val city = getCityNameFromCoordinates(freshLocation.latitude, freshLocation.longitude)
                                        continuation.resume(
                                            UserLocationData(
                                                latitude = freshLocation.latitude,
                                                longitude = freshLocation.longitude,
                                                accuracyMeters = freshLocation.accuracy,
                                                cityName = city
                                            )
                                        )
                                    } else {
                                        continuation.resume(null)
                                    }
                                }
                                .addOnFailureListener {
                                    continuation.resume(null)
                                }
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(null)
                    }
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocationUpdates(intervalMs: Long = 5000L): Flow<UserLocationData> = callbackFlow {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalMs)
            .setMinUpdateIntervalMillis(intervalMs / 2)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                val city = getCityNameFromCoordinates(location.latitude, location.longitude)
                trySend(
                    UserLocationData(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        accuracyMeters = location.accuracy,
                        cityName = city
                    )
                )
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, callback, context.mainLooper)
        } catch (e: Exception) {
            close(e)
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }

    fun getCityNameFromCoordinates(lat: Double, lng: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale("tr", "TR"))
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                address.adminArea ?: address.subAdminArea ?: address.locality
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        fun calculateDistanceKm(
            startLat: Double,
            startLng: Double,
            endLat: Double,
            endLng: Double
        ): Double {
            val results = FloatArray(1)
            Location.distanceBetween(startLat, startLng, endLat, endLng, results)
            return results[0].toDouble() / 1000.0
        }
    }
}
