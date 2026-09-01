package com.example.data.local

import com.example.data.model.FavoriteStopEntity
import com.example.data.model.SavedTripEntity
import kotlinx.coroutines.flow.Flow

class TripRepository(private val tripDao: TripDao) {
    val savedTrips: Flow<List<SavedTripEntity>> = tripDao.getAllSavedTrips()
    val favorites: Flow<List<FavoriteStopEntity>> = tripDao.getAllFavorites()

    suspend fun saveTrip(trip: SavedTripEntity): Long {
        return tripDao.insertTrip(trip)
    }

    suspend fun deleteTrip(tripId: Long) {
        tripDao.deleteTrip(tripId)
    }

    suspend fun toggleFavorite(stopId: String, name: String, city: String, category: String, rating: Double) {
        if (tripDao.isFavorite(stopId)) {
            tripDao.deleteFavorite(stopId)
        } else {
            tripDao.insertFavorite(
                FavoriteStopEntity(
                    stopId = stopId,
                    name = name,
                    city = city,
                    category = category,
                    rating = rating
                )
            )
        }
    }

    suspend fun isFavorite(stopId: String): Boolean {
        return tripDao.isFavorite(stopId)
    }
}
