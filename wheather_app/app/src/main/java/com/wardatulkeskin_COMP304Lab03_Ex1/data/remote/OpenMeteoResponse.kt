package com.wardatulkeskin_COMP304Lab03_Ex1.data.remote

/**
 * Top-level response from Open-Meteo /v1/forecast endpoint
 * Example:
 * https://api.open-meteo.com/v1/forecast
 *   ?latitude=52.52
 *   &longitude=13.41
 *   &current=temperature_2m,wind_speed_10m,is_day,rain
 *   &timezone=auto
 */
data class OpenMeteoResponse(
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val timezone_abbreviation: String?,
    val current_units: CurrentUnits?,  // units for the "current" block
    val current: Current?              // actual current weather values
)

/**
 * Units for the "current" fields, e.g. "°C", "km/h"
 */
data class CurrentUnits(
    val time: String?,
    val interval: String?,
    val temperature_2m: String?,
    val wind_speed_10m: String?,
    val is_day: String?,
    val rain: String?
)

/**
 * Current weather values for the requested coordinates.
 */
data class Current(
    val time: String?,
    val interval: Int?,
    val temperature_2m: Double,
    val wind_speed_10m: Double,
    val is_day: Int,
    val rain: Double
)
