package com.wardatulkeskin_COMP304Lab03_Ex1.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherListItemUi
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherListUiState
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherListViewModel

/**
* Top-level composable for the Weather List screen.
*
* Responsibilities:
* - Collect UI state from [WeatherListViewModel]
* - Forward user actions (refresh, item click) back to the ViewModel / navigation
*/
@Composable
fun WeatherListScreen(
    viewModel: WeatherListViewModel,
    onLocationClick: (Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    WeatherListContent(
        state = state,
        onRefresh = { viewModel.refreshAllLocations() },
        onLocationClick = onLocationClick
    )
}

/**
 * Stateless representation of the list screen.
 * This makes it easier to preview & test.
 */
@Composable
private fun WeatherListContent(
    state: WeatherListUiState,
    onRefresh: () -> Unit,
    onLocationClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Weather Locations",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tap a city to see more details",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Simple refresh indicator (text clickable)
            Text(
                text = "Refresh",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onRefresh() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Error text
        state.error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Loading indicator
        if (state.isLoading && state.items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.items) { item ->
                    WeatherLocationCard(
                        item = item,
                        onClick = { onLocationClick(item.id) }
                    )
                }
            }
        }
    }
}

/**
 * Single item card showing city name, condition, temperature, and last updated.
 */
@Composable
private fun WeatherLocationCard(
    item: WeatherListItemUi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.cityName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (item.conditionText.isNotBlank()) {
                        Text(
                            text = item.conditionText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Text(
                    text = item.temperatureText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (item.lastUpdatedText.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.lastUpdatedText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
