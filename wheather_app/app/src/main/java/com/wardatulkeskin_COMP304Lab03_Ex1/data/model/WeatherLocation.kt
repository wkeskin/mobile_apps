package com.wardatulkeskin_COMP304Lab03_Ex1.data.model

/**
 * Represents a fixed weather location with coordinates.
 *
 * This model is used:
 * - by the UI to display a list of cities
 * - by the repository to call the Open-Meteo API using latitude/longitude
 *
 * Note: This is NOT a Room entity. The "dynamic" weather values are stored
 * in [WeatherLocationEntity] instead.
 */
data class WeatherLocation(
    val id: Long,
    val cityName: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

/**
 * 7 default cities for the Weather App.
 * These will never change, so they stay in code.
 */
val DefaultLocations = listOf(
    WeatherLocation(
        id = 1,
        cityName = "London, ON",
        country = "Canada",
        latitude = 42.9849,
        longitude = -81.2453
    ),
    WeatherLocation(
        id = 2,
        cityName = "Toronto, ON",
        country = "Canada",
        latitude = 43.65107,
        longitude = -79.347015
    ),
    WeatherLocation(
        id = 3,
        cityName = "Bandung",
        country = "Indonesia",
        latitude = -6.9175,
        longitude = 107.6191
    ),
    WeatherLocation(
        id = 4,
        cityName = "Istanbul",
        country = "Turkey",
        latitude = 41.0082,
        longitude = 28.9784
    ),
    WeatherLocation(
        id = 5,
        cityName = "Tokyo",
        country = "Japan",
        latitude = 35.6762,
        longitude = 139.6503
    ),
    WeatherLocation(
        id = 6,
        cityName = "Sydney",
        country = "Australia",
        latitude = -33.8688,
        longitude = 151.2093
    ),
    WeatherLocation(
        id = 7,
        cityName = "Amsterdam",
        country = "Netherlands",
        latitude = 52.3676,
        longitude = 4.9041
    )
)
