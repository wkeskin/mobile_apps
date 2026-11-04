package com.wardatulkeskin_COMP304Lab03_Ex1.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity that stores the latest weather data for a location.
 *
 * We only store:
 * - basic city info (cityName, country)
 * - current weather snapshot (temperature, condition, lastUpdated)
 *
 * The coordinates (lat/lon) come from [WeatherLocation] (DefaultLocations).
 */
@Entity(tableName = "weather_locations")
data class WeatherLocationEntity(
    /**
     * Primary key for this row.
     *
     * We intentionally DO NOT auto-generate the id, so we can reuse the same
     * ids defined in [DefaultLocations] (1..7). This ensures that each city
     * always maps to the same database row.
     */
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val cityName: String,
    val country: String? = null,
    // Latest temperature value returned from the API (°C).
    val temperature: Double? = null,
    // Simple text condition used for display (e.g., "Raining", "Clear").
    val condition: String? = null,
    // Epoch millis when this record was last updated.
    val lastUpdated: Long = System.currentTimeMillis()
)