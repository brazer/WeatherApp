package com.salanevich.weatherapp.data.response

import com.squareup.moshi.Json

data class Day(

	@Json(name="avgvis_km")
	val avgvisKm: Double? = null,

	@Json(name="uv")
	val uv: Double? = null,

	@Json(name="avgtemp_f")
	val avgtempF: Double? = null,

	@Json(name="avgtemp_c")
	val avgtempC: Double? = null,

	@Json(name="daily_chance_of_snow")
	val dailyChanceOfSnow: Int? = null,

	@Json(name="maxtemp_c")
	val maxtempC: Double? = null,

	@Json(name="maxtemp_f")
	val maxtempF: Double? = null,

	@Json(name="mintemp_c")
	val mintempC: Double? = null,

	@Json(name="avgvis_miles")
	val avgvisMiles: Double? = null,

	@Json(name="daily_will_it_rain")
	val dailyWillItRain: Int? = null,

	@Json(name="mintemp_f")
	val mintempF: Double? = null,

	@Json(name="totalprecip_in")
	val totalprecipIn: Double? = null,

	@Json(name="avghumidity")
	val avghumidity: Double? = null,

	@Json(name="condition")
	val condition: Condition? = null,

	@Json(name="maxwind_kph")
	val maxwindKph: Double? = null,

	@Json(name="maxwind_mph")
	val maxwindMph: Double? = null,

	@Json(name="daily_chance_of_rain")
	val dailyChanceOfRain: Int? = null,

	@Json(name="totalprecip_mm")
	val totalprecipMm: Double? = null,

	@Json(name="daily_will_it_snow")
	val dailyWillItSnow: Int? = null
)