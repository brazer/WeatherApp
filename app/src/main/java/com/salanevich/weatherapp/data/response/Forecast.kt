package com.salanevich.weatherapp.data.response

import com.squareup.moshi.Json

data class Forecast(

	@Json(name="forecastday")
	val forecastday: List<ForecastdayItem?>? = null
)