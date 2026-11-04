package com.wardatulkeskin_COMP304Lab03_Ex1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main Room database for the Weather app.
 *
 * Contains a single table: [WeatherLocationEntity].
 * Exposes a single DAO: [WeatherDao].
 */
@Database(
    entities = [WeatherLocationEntity::class],
    version = 1,
    exportSchema = false // set to false to avoid schema export warnings
)
abstract class WeatherDatabase : RoomDatabase() {
    /**
     * Provides access to all CRUD operations for weather locations.
     */
    abstract fun weatherDao(): WeatherDao
}