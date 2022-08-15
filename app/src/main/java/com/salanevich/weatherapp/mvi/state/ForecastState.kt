package com.salanevich.weatherapp.mvi.state

import com.salanevich.weatherapp.ui.model.ForecastItemDetailsModel
import com.salanevich.weatherapp.ui.model.ForecastItemModel

sealed class ForecastState: WeatherState() {
    object Loading: ForecastState()
    object EmptyData: ForecastState()
    data class LoadedData(val list: List<ForecastItemModel>): ForecastState()
    data class NavigatingToDetails(val item: ForecastItemDetailsModel): ForecastState()
}