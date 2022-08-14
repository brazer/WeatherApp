package com.salanevich.weatherapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.salanevich.weatherapp.ui.model.ForecastItemModel
import com.salanevich.weatherapp.ui.theme.WeatherAppTheme
import com.salanevich.weatherapp.utils.getDate
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun ForecastScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    location: String
) {
    ForecastList(data = listOf())
}

@Composable
fun ForecastList(
    modifier: Modifier = Modifier,
    data: List<ForecastItemModel>
) {
    LazyColumn(modifier = modifier) {
        data.forEachIndexed { index, forecastItemModel ->
            item {
                ForecastItem(item = forecastItemModel, position = index, onClick = { /*TODO*/ })
            }
        }
    }
}

@Composable
fun ForecastItem(
    modifier: Modifier = Modifier,
    item: ForecastItemModel,
    position: Int,
    onClick: (ForecastItemModel) -> Unit
) {
    Column(modifier = modifier.clickable { onClick(item) }) {
        AsyncImage(model = item.weatherIconUrl, contentDescription = "weather icon")
        Text(text = getDate(item.date, position, LocalContext.current.resources))
        Text(item.temperature)
    }
}

@Preview
@Composable
fun ForecastListPreview() {
    val list = ArrayList<ForecastItemModel>()
    repeat(5) {
        val item = ForecastItemModel(Date(), "32°C", "https://cdn.weatherapi.com/weather/64x64/day/113.png")
        list.add(item)
    }
    WeatherAppTheme {
        Surface {
            ForecastList(data = list)
        }
    }
}