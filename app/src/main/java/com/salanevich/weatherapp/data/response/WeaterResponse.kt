package com.salanevich.weatherapp.data.response

import com.squareup.moshi.Json

data class WeaterResponse(

	@Json(name="current")
	val current: Current? = null,

	@Json(name="location")
	val location: Location? = null,

	@Json(name="forecast")
	val forecast: Forecast? = null
)