package com.wardatulkeskin_COMP304Lab03_Ex1.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service definition for calling the Open-Meteo API.
 * Uses Kotlin coroutines (suspend functions) for async network calls.
 */

interface WeatherApi {
    /**
     * Fetches current weather for the given coordinates.
     *
     * @param latitude   Geographic latitude of the city
     * @param longitude  Geographic longitude of the city
     * @param current    Comma-separated list of current weather fields to return
     * @param timezone   Timezone for the response, "auto" lets the API choose
     */
        @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current")
        current: String = "temperature_2m,wind_speed_10m,is_day,rain",
        @Query("timezone")
        timezone: String = "auto"
    ): OpenMeteoResponse
}