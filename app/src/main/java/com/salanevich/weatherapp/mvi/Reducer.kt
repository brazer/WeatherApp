package com.salanevich.weatherapp.mvi

import com.salanevich.weatherapp.mvi.intent.WeatherIntent
import com.salanevich.weatherapp.mvi.state.WeatherState

interface Reducer<Intent : WeatherIntent, State : WeatherState> {
    suspend fun reduce(intent: Intent, currentState: State)
}