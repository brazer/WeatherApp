package com.salanevich.weatherapp.mvi.state

sealed class MainState: WeatherState() {
    object Loading: MainState()
    data class Loaded(val locations: List<String>): MainState()
    data class NavigatingToForecast(val location: String): MainState()
}