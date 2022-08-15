package com.salanevich.weatherapp.data.response

import com.google.gson.annotations.SerializedName

data class Forecast(

	@field:SerializedName("forecastday")
	val forecastday: List<ForecastdayItem?>? = null
)