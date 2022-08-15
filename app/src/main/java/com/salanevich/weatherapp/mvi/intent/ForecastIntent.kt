package com.salanevich.weatherapp.mvi.intent

sealed class ForecastIntent: WeatherIntent() {
    data class Load(val location: String): ForecastIntent()
    data class NavigateToDetails(val position: Int): ForecastIntent()
}