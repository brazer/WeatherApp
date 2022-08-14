package com.salanevich.weatherapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salanevich.weatherapp.data.store.WeatherDataStore
import com.salanevich.weatherapp.mvi.Reducer
import com.salanevich.weatherapp.mvi.intent.MainIntent
import com.salanevich.weatherapp.mvi.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val store: WeatherDataStore //TODO: implement repository instead
): ViewModel(), Reducer<MainIntent, MainState> {

    val stateFlow: StateFlow<MainState> = MutableStateFlow(MainState.Loading)
    private val _state = stateFlow as MutableStateFlow

    override suspend fun reduce(intent: MainIntent, currentState: MainState) {
        when (intent) {
            is MainIntent.LoadData -> {
                val locations = store.getLocations()
                _state.value = MainState.Loaded(locations.toList())
            }
            is MainIntent.AddLocation -> {
                store.addLocation(intent.location)
                _state.value = MainState.Loading
            }
            is MainIntent.OpenForecast -> {
                _state.value = MainState.NavigatingToForecast(intent.location)
            }
        }
    }

}