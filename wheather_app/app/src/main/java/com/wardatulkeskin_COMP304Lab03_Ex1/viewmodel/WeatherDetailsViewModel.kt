package com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wardatulkeskin_COMP304Lab03_Ex1.data.model.DefaultLocations
import com.wardatulkeskin_COMP304Lab03_Ex1.data.remote.NetworkResult
import com.wardatulkeskin_COMP304Lab03_Ex1.data.repository.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel for WeatherDetailsScreen.
 *
 * Responsibilities:
 * - Load specific location details from Room (reactively)
 * - Trigger a weather refresh via Repository
 * - Maintain UI state through StateFlow
 */
data class WeatherDetailsUiState(
    val cityName: String = "",
    val country: String = "",
    val temperatureText: String = "",
    val conditionText: String = "",
    val lastUpdatedText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class WeatherDetailsViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    // UI state exposed to the Composable screen
    private val _uiState = MutableStateFlow(WeatherDetailsUiState())
    val uiState: StateFlow<WeatherDetailsUiState> = _uiState

    private var observeJob: Job? = null
    private var currentLocationId: Long? = null

    /**
     * Loads weather for a specific location.
     * Observes Room AND triggers refresh from network.
     */
    fun loadLocation(locationId: Long) {
        if (currentLocationId == locationId) return
        currentLocationId = locationId

        val location = DefaultLocations.firstOrNull { it.id == locationId }
        if (location == null) {
            _uiState.update { it.copy(error = "Unknown location") }
            return
        }

        // Initial UI state setup
        _uiState.update {
            it.copy(
                cityName = location.cityName,
                country = location.country,
                isLoading = true
            )
        }

        // Observe Room changes for this ID
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            repository.getLocation(locationId).collectLatest { entity ->
                if (entity != null) {
                    _uiState.update { state ->
                        state.copy(
                            cityName = entity.cityName,
                            country = entity.country ?: location.country,
                            temperatureText = entity.temperature?.let { "%.1f°C".format(it) }
                                ?: "-- °C",
                            conditionText = entity.condition ?: "Unknown",
                            lastUpdatedText = formatLastUpdated(entity.lastUpdated)
                        )
                    }
                }
            }
        }

        // Trigger refresh
        viewModelScope.launch {
            when (val result = repository.refreshWeatherForLocation(location)) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
                NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun formatLastUpdated(millis: Long): String {
        if (millis <= 0L) return ""
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return "Last updated: ${f.format(Date(millis))}"
    }
}
