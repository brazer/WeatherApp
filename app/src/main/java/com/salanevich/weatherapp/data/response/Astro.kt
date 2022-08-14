package com.salanevich.weatherapp.data.response

import com.squareup.moshi.Json

data class Astro(

	@Json(name="moonset")
	val moonset: String? = null,

	@Json(name="moon_illumination")
	val moonIllumination: String? = null,

	@Json(name="sunrise")
	val sunrise: String? = null,

	@Json(name="moon_phase")
	val moonPhase: String? = null,

	@Json(name="sunset")
	val sunset: String? = null,

	@Json(name="moonrise")
	val moonrise: String? = null
)