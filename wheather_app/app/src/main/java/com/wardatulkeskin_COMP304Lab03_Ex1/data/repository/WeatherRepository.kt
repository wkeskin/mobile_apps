package com.wardatulkeskin_COMP304Lab03_Ex1.data.repository

import kotlinx.coroutines.flow.Flow
import com.wardatulkeskin_COMP304Lab03_Ex1.data.local.WeatherLocationEntity
import com.wardatulkeskin_COMP304Lab03_Ex1.data.remote.NetworkResult
import com.wardatulkeskin_COMP304Lab03_Ex1.data.model.WeatherLocation

/**
 * Repository interface that abstracts access to weather data.
 *
 * ViewModels depend on this interface instead of concrete implementations,
 * which makes the app easier to test and follow the MVVM pattern.
 */
interface WeatherRepository {

    /**
     * Reactive stream of all saved locations from Room.
     */
    fun getSavedLocations(): Flow<List<WeatherLocationEntity>>

    /**
     * Reactive stream for a single location by [id].
     */
    fun getLocation(id: Long): Flow<WeatherLocationEntity?>

    /**
     * Calls the network API to refresh weather for [location],
     * then stores the result in Room.
     *
     * Returns a [NetworkResult] so the UI can show loading/error states.
     */
    suspend fun refreshWeatherForLocation(
        location: WeatherLocation
    ): NetworkResult<Unit>

    /**
     * Deletes a location from the local database.
     */
    suspend fun deleteLocation(location: WeatherLocationEntity)
}
