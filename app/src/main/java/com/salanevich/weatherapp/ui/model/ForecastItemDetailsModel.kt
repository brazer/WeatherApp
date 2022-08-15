package com.salanevich.weatherapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastItemDetailsModel(
    val date: String,
    val temperatureDay: String?,
    val temperatureNight: String?,
    val temperatureFeel: String?,
    val weatherIconUrl: String,
    val humidity: String?,
    val pressure: String?,
    val uvIndex: Float?,
    val sunriseTime: String?,
    val sunsetTime: String?
): Parcelable
