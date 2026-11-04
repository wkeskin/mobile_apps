package com.wardatulkeskin_COMP304Lab03_Ex1.navigation

/**
 * Defines application routes used in navigation.
 *
 * Using sealed classes provides type safety and cleaner route definitions.
 */
sealed class Screen(val route: String) {

    // List of weather locations
    data object WeatherList : Screen("weather_list")

    // Weather detail screen with locationId argument
    data object WeatherDetails : Screen("weather_details/{locationId}") {
        // Creates full route string for navigating with an ID
        fun createRoute(locationId: Long): String = "weather_details/$locationId"
    }
}
