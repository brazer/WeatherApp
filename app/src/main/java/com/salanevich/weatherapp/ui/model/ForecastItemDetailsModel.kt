package com.salanevich.weatherapp.ui.model

data class ForecastItemDetailsModel(
    val temperatureDay: String,
    val temperatureNight: String,
    val temperatureFeel: String,
    val weatherIconUrl: String,
    val humidity: String,
    val pressure: String,
    val uvIndex: Float,
    val sunriseTime: String,
    val sunsetTime: String
)
