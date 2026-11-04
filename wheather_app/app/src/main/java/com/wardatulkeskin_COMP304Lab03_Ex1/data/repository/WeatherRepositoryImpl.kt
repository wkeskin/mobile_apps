package com.wardatulkeskin_COMP304Lab03_Ex1.data.repository

import com.wardatulkeskin_COMP304Lab03_Ex1.data.local.WeatherDao
import com.wardatulkeskin_COMP304Lab03_Ex1.data.local.WeatherLocationEntity
import com.wardatulkeskin_COMP304Lab03_Ex1.data.model.WeatherLocation
import com.wardatulkeskin_COMP304Lab03_Ex1.data.remote.NetworkResult
import com.wardatulkeskin_COMP304Lab03_Ex1.data.remote.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import android.util.Log

/**
 * Concrete implementation of [WeatherRepository].
 *
 * - Uses [WeatherApi] (Retrofit) to fetch data from Open-Meteo.
 * - Uses [WeatherDao] (Room) to cache the latest weather locally.
 */
class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    /**
     * Exposes all saved locations from Room as a Flow.
     */
    override fun getSavedLocations(): Flow<List<WeatherLocationEntity>> =
        dao.getAllLocations()

    /**
     * Exposes a single location (by id) as a Flow.
     */
    override fun getLocation(id: Long): Flow<WeatherLocationEntity?> =
        dao.getLocationById(id)

    /**
     * Fetches the current weather for [location] from the API
     * and saves it into Room.
     *
     * All work is done on the IO dispatcher so the main thread is not blocked.
     */
    override suspend fun refreshWeatherForLocation(
        location: WeatherLocation
    ): NetworkResult<Unit> = withContext(Dispatchers.IO) {
        try {
            // 1. Call the Open-Meteo API using the coordinates.
            val response = api.getCurrentWeather(
                latitude = location.latitude,
                longitude = location.longitude
            )

            val current = response.current

            // 2. Convert raw values into a simple "condition" string for the UI.
            val conditionText = when {
                current?.rain != null && current.rain > 0.0 -> "Raining"
                current?.is_day == 1 -> "Clear"
                current?.is_day == 0 -> "Night"
                else -> "Unknown"
            }

            // 3. Build the Room entity using the fixed ID from DefaultLocations.
            val entity = WeatherLocationEntity(
                id = location.id,
                cityName = location.cityName,
                country = location.country,
                temperature = current?.temperature_2m,
                condition = conditionText,
                lastUpdated = System.currentTimeMillis()
            )

            // 4. Upsert into the local database.
            dao.upsertLocation(entity)

            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            // Log full error for Logcat debugging
            Log.e("WeatherRepository", "Error refreshing ${location.cityName}", e)

            val reason = e.localizedMessage ?: e::class.simpleName ?: "Unknown error"

            NetworkResult.Error(
                message = "Failed to refresh weather for ${location.cityName}: $reason",
                throwable = e
            )
        }
    }

    /**
     * Deletes the given [location] from Room.
     */
    override suspend fun deleteLocation(location: WeatherLocationEntity) {
        dao.deleteLocation(location)
    }
}
