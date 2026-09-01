package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class WaypointCategory(val titleTr: String, val iconName: String) {
    HISTORICAL("Tarihi Yerler", "Castle"),
    NATURE("Doğal Güzellikler", "Park"),
    RESTAURANT("Favori Restoranlar", "Restaurant"),
    SCENIC("Manzaralı Duraklar", "PhotoCamera")
}

data class FacilityAmenity(
    val id: String,
    val nameTr: String,
    val iconKey: String
)

data class UserReview(
    val author: String,
    val source: String, // e.g. "Google Maps", "TripAdvisor", "Gezginler Kulübü"
    val rating: Double,
    val date: String,
    val comment: String
)

data class WaypointStop(
    val id: String,
    val name: String,
    val subtitle: String,
    val city: String,
    val category: WaypointCategory,
    val lat: Double,
    val lng: Double,
    val kmFromStart: Int,
    val drivingMinutesFromStart: Int,
    val recommendedStayMinutes: Int = 45,
    val openTime: String = "08:30",
    val closeTime: String = "19:00",
    val alwaysOpen: Boolean = false,
    val feeDescription: String, // e.g. "Ücretsiz", "Müzekart Geçerli / 160 ₺", "Ortalama 350-500 ₺ / Kişi"
    val isFree: Boolean = false,
    val museumCardAccepted: Boolean = false,
    val averageRating: Double = 4.8,
    val reviewCount: Int = 1240,
    val amenities: List<String> = listOf("Otopark", "WC", "Kafeterya", "Wi-Fi"),
    val guideImportantNotes: List<String> = emptyList(), // "Rehber gibi gidince bilinmesi gerekenler"
    val description: String,
    val audioGuideTranscript: String = "",
    val reviews: List<UserReview> = emptyList(),
    val isSelectedForTrip: Boolean = true
)

data class RouteStatistics(
    val totalKm: Int,
    val totalDrivingMinutes: Int,
    val totalStayMinutes: Int,
    val totalSelectedStops: Int,
    val departureTimeFormatted: String,
    val estimatedArrivalTimeFormatted: String
)

@Entity(tableName = "saved_trips")
data class SavedTripEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val origin: String,
    val destination: String,
    val departureTimestamp: Long,
    val totalDistanceKm: Int,
    val totalStopsCount: Int,
    val stopsJson: String, // serialized stops
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorite_stops")
data class FavoriteStopEntity(
    @PrimaryKey val stopId: String,
    val name: String,
    val city: String,
    val category: String,
    val rating: Double,
    val addedAt: Long = System.currentTimeMillis()
)
