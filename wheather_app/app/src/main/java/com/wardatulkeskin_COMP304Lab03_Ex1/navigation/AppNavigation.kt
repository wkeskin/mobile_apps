package com.wardatulkeskin_COMP304Lab03_Ex1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherDetailsViewModel
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherListViewModel
import com.wardatulkeskin_COMP304Lab03_Ex1.views.WeatherDetailsScreen
import com.wardatulkeskin_COMP304Lab03_Ex1.views.WeatherListScreen
import org.koin.androidx.compose.koinViewModel

/**
 * Main navigation graph of the Weather App.
 *
 * Uses Jetpack Navigation Compose.
 * Contains two destinations:
 *  - WeatherListScreen (start destination)
 *  - WeatherDetailsScreen (takes locationId argument)
 */
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.WeatherList.route,
        modifier = modifier
    ) {
        // List screen
        composable(route = Screen.WeatherList.route) {
            val viewModel: WeatherListViewModel = koinViewModel()

            WeatherListScreen(
                viewModel = viewModel,
                onLocationClick = { locationId ->
                    navController.navigate(
                        Screen.WeatherDetails.createRoute(locationId)
                    )
                }
            )
        }

        // Details screen
        composable(
            route = Screen.WeatherDetails.route,
            arguments = listOf(
                navArgument("locationId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val viewModel: WeatherDetailsViewModel = koinViewModel()

            val locationId = backStackEntry.arguments?.getLong("locationId") ?: 0L

            WeatherDetailsScreen(
                locationId = locationId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
