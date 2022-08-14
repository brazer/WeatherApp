package com.salanevich.weatherapp.ui.model

import java.util.*

data class ForecastItemModel(
    val date: Date,
    val temperature: String,
    val weatherIconUrl: String
)
