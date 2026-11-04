package com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wardatulkeskin_COMP304Lab03_Ex1.data.model.DefaultLocations
import com.wardatulkeskin_COMP304Lab03_Ex1.data.remote.NetworkResult
import com.wardatulkeskin_COMP304Lab03_Ex1.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel for the Weather List screen.
 *
 * Responsibilities:
 * - Observe all weather entries from Room
 * - Trigger network refresh for all 7 default locations
 * - Expose UI data with StateFlow
 */
data class WeatherListItemUi(
    val id: Long,
    val cityName: String,
    val temperatureText: String,
    val conditionText: String,
    val lastUpdatedText: String
)

data class WeatherListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<WeatherListItemUi> = emptyList()
)

class WeatherListViewModel(
    private val repository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherListUiState())
    val uiState: StateFlow<WeatherListUiState> = _uiState

    init {
        observeWeatherChanges()
        refreshAllLocations()
    }

    /** Observes all Room weather entries and updates UI list */
    private fun observeWeatherChanges() {
        viewModelScope.launch {
            repository.getSavedLocations().collectLatest { entities ->

                val items: List<WeatherListItemUi> =
                    if (entities.isEmpty()) {
                        // No data in Room yet → show default cities with placeholder values
                        DefaultLocations.map { location ->
                            WeatherListItemUi(
                                id = location.id,
                                cityName = "${location.cityName}, ${location.country}",
                                temperatureText = "-- °C",   // not loaded yet
                                conditionText = "",
                                lastUpdatedText = ""
                            )
                        }
                    } else {
                        // Room has data → map entities to UI
                        entities.map { entity ->
                            WeatherListItemUi(
                                id = entity.id,
                                cityName = entity.cityName,
                                temperatureText = entity.temperature?.let { "%.1f°C".format(it) }
                                    ?: "-- °C",
                                conditionText = entity.condition ?: "",
                                lastUpdatedText = formatLastUpdated(entity.lastUpdated)
                            )
                        }
                    }

                _uiState.value = _uiState.value.copy(items = items)
            }
        }
    }

    /** Refresh weather for all 7 default cities */
    fun refreshAllLocations() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch(ioDispatcher) {
            DefaultLocations.forEach { location ->
                when (val result = repository.refreshWeatherForLocation(location)) {
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                        return@launch
                    }
                    else -> Unit
                }
            }

            // Done loading
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private fun formatLastUpdated(millis: Long): String {
        if (millis <= 0L) return ""
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return "Updated: " + formatter.format(Date(millis))
    }
}
