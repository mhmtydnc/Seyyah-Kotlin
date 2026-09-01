package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.FavoriteStopEntity
import com.example.data.model.SavedTripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM saved_trips ORDER BY createdAt DESC")
    fun getAllSavedTrips(): Flow<List<SavedTripEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: SavedTripEntity): Long

    @Query("DELETE FROM saved_trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: Long)

    @Query("SELECT * FROM favorite_stops ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteStopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(stop: FavoriteStopEntity)

    @Query("DELETE FROM favorite_stops WHERE stopId = :stopId")
    suspend fun deleteFavorite(stopId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_stops WHERE stopId = :stopId)")
    suspend fun isFavorite(stopId: String): Boolean
}
