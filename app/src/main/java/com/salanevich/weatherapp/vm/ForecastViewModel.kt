package com.salanevich.weatherapp.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.salanevich.weatherapp.data.NetworkService
import com.salanevich.weatherapp.data.map
import com.salanevich.weatherapp.di.ApiKey
import com.salanevich.weatherapp.mvi.Reducer
import com.salanevich.weatherapp.mvi.intent.ForecastIntent
import com.salanevich.weatherapp.mvi.state.ForecastState
import com.salanevich.weatherapp.ui.model.ForecastItemDetailsModel
import com.salanevich.weatherapp.ui.model.ForecastItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val apiService: NetworkService, //TODO: implement repository instead
    @ApiKey private val apiKey: String
): ViewModel(), Reducer<ForecastIntent, ForecastState> {

    companion object {
        private const val DAYS = 7
    }

    val stateFlow: StateFlow<ForecastState> = MutableStateFlow(ForecastState.Loading)
    private val _state = stateFlow as MutableStateFlow
    private lateinit var details: List<ForecastItemDetailsModel>

    override suspend fun reduce(intent: ForecastIntent, currentState: ForecastState) {
        when (intent) {
            is ForecastIntent.Load -> {
                try {
                    getForecast(intent.location).collect()
                } catch (e: IOException) {
                    Log.e("WeatherApp", "ForecastViewModel.reduce: $intent", e)
                    _state.value = ForecastState.EmptyData
                }
            }
            is ForecastIntent.NavigateToDetails -> {
                _state.value = ForecastState.NavigatingToDetails(details[intent.position])
            }
        }
    }

    private fun getForecast(location: String) = flow<Nothing> {
        val response = apiService.getForecast(apiKey, location, DAYS)
        details = response.map()
        if (details.isEmpty()) {
            _state.value = ForecastState.EmptyData
        } else _state.value = ForecastState.LoadedData(details.map())
    }

}