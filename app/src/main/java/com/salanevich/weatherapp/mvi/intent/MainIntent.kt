package com.salanevich.weatherapp.mvi.intent

sealed class MainIntent: WeatherIntent() {
    object LoadData: MainIntent()
    data class AddLocation(val location: String): MainIntent()
    data class OpenForecast(val location: String): MainIntent()
}