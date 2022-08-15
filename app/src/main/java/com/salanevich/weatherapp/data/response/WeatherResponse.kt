package com.salanevich.weatherapp.data.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("forecast")
	val forecast: Forecast? = null
)