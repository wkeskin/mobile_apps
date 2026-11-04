package com.wardatulkeskin_COMP304Lab03_Ex1.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherDetailsUiState
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherDetailsViewModel

/**
 * Top-level composable for the Weather Details screen.
 *
 * Loads data for the given [locationId] and displays a detailed view.
 */
@Composable
fun WeatherDetailsScreen(
    locationId: Long,
    viewModel: WeatherDetailsViewModel,
    onBack: () -> Unit
) {
    // Load data when we enter this screen
    LaunchedEffect(locationId) {
        viewModel.loadLocation(locationId)
    }

    val state by viewModel.uiState.collectAsState()

    WeatherDetailsContent(
        state = state,
        onBack = onBack
    )
}

/**
 * Stateless UI for showing details of a single location.
 */
@Composable
private fun WeatherDetailsContent(
    state: WeatherDetailsUiState,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // City + country
            Text(
                text = state.cityName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            if (state.country.isNotBlank()) {
                Text(
                    text = state.country,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Loading / error / main content
            if (state.isLoading && state.temperatureText.isBlank()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Text(
                    text = state.temperatureText.ifBlank { "-- °C" },
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                if (state.conditionText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.conditionText,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                if (state.lastUpdatedText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.lastUpdatedText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                state.error?.let { errorText ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorText,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Back button at bottom
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onBack) {
                Text(text = "Back")
            }
        }
    }
}
