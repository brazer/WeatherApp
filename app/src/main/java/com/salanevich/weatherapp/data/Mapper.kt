package com.salanevich.weatherapp.data

import com.salanevich.weatherapp.data.response.HourItem
import com.salanevich.weatherapp.data.response.WeatherResponse
import com.salanevich.weatherapp.ui.model.ForecastItemDetailsModel
import com.salanevich.weatherapp.ui.model.ForecastItemModel
import com.salanevich.weatherapp.utils.GRADE_CELSIUS
import java.util.*

fun WeatherResponse.map(): List<ForecastItemDetailsModel> {
    val list = LinkedList<ForecastItemDetailsModel>()
    forecast?.forecastday?.forEach { item ->
        val day = item?.day ?: return emptyList()
        val hours = item.hour ?: return emptyList()
        val temperatureAtNight = calculateApproximatedTemperatureAtNight(hours)
        val temperatureFeelsLike = calculateTemperatureFeelsLike(hours)
        val weatherIcon = if (day.condition?.icon == null) "" else "https:${day.condition.icon}"
        val element = ForecastItemDetailsModel(
            date = item.date ?: return emptyList(),
            temperatureDay = if (day.avgtempC != null) "${day.avgtempC}$GRADE_CELSIUS" else null,
            temperatureNight = if (temperatureAtNight != null) "$temperatureAtNight$GRADE_CELSIUS" else null,
            temperatureFeel = if (temperatureFeelsLike != null) "$temperatureFeelsLike$GRADE_CELSIUS" else null,
            weatherIconUrl = weatherIcon,
            humidity = if (day.avghumidity != null) day.avghumidity.toString() else null,
            pressure = if (day.totalprecipMm != null) "${day.totalprecipMm}" else null,
            uvIndex = day.uv?.toFloat(),
            sunriseTime = item.astro?.sunrise,
            sunsetTime = item.astro?.sunset
        )
        list.add(element)
    }
    return list
}

private fun calculateApproximatedTemperatureAtNight(hours: List<HourItem>): Float? {
    val list = hours.take(12).filter { it.tempC != null }.map { checkNotNull(it.tempC).toFloat() }
    return if (list.isEmpty()) null else list.average().toFloat()
}

private fun calculateTemperatureFeelsLike(hours: List<HourItem>): Float? {
    val list = hours.filter { it.feelslikeC != null }.map { checkNotNull(it.feelslikeC) }
    return if (list.isEmpty()) null else list.average().toFloat()
}

fun List<ForecastItemDetailsModel>.map(): List<ForecastItemModel> {
    return map {
        ForecastItemModel(
            date = it.date,
            temperature = it.temperatureDay,
            weatherIconUrl = it.weatherIconUrl
        )
    }
}