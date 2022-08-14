package com.salanevich.weatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("forecast.json")
    fun getForecast(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    )

}