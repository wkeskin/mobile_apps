package com.wardatulkeskin_COMP304Lab03_Ex1.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the `weather_locations` table.
 *
 * - Exposes reactive [Flow] queries for use in ViewModels.
 * - Allows inserting/updating (upsert) and deleting locations.
 */

@Dao
interface WeatherDao {
    /**
     * Returns all saved locations sorted by city name.
     *
     * The Flow will emit a new list whenever the underlying table changes,
     * which is perfect for Jetpack Compose + MVVM.
     */
    @Query("SELECT * FROM weather_locations ORDER BY cityName ASC")
    fun getAllLocations(): Flow<List<WeatherLocationEntity>>

    /**
     * Returns a single location by [id], or null if not found.
     */
    @Query("SELECT * FROM weather_locations WHERE id = :id")
    fun getLocationById(id: Long): Flow<WeatherLocationEntity?>

    /**
     * Inserts a new location or updates it if the primary key already exists.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLocation(location: WeatherLocationEntity)

    /**
     * Deletes a specific location from the table.
     */
    @Delete
    suspend fun deleteLocation(location: WeatherLocationEntity)

    /**
     * Deletes all rows from the table.
     * Useful for "clear all" or testing.
     */
    @Query("DELETE FROM weather_locations")
    suspend fun clearAll()



}
