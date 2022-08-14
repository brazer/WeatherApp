package com.salanevich.weatherapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.salanevich.weatherapp.ui.model.ForecastItemDetailsModel
import com.salanevich.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    day: String
) {

}

@Composable
fun DetailsView(
    modifier: Modifier = Modifier,
    data: ForecastItemDetailsModel
) {
    Column(modifier = modifier) {
        AsyncImage(model = data.weatherIconUrl, contentDescription = "weather icon")
        Text(text = "Temperature day: ${data.temperatureDay}")
        Text(text = "Temperature night: ${data.temperatureNight}")
        Text(text = "Temperature files like ${data.temperatureFeel}")
        Text(text = "Humidity: ${data.humidity}")
        Text(text = "Pressure: ${data.pressure}")
        Text(text = "UV index: ${data.uvIndex}")
        Text(text = "Sunrise time: ${data.sunriseTime}")
        Text(text = "Sunset time: ${data.sunsetTime}")
    }
}

@Preview
@Composable
fun DetailsViewPreview() {
    val data = ForecastItemDetailsModel(
        "32°C", "24°C", "28°C", "https://cdn.weatherapi.com/weather/64x64/day/113.png", "42", "1015.0", 1.0f, "05:43 AM", "08:27 PM"
    )
    WeatherAppTheme {
        Surface {
            DetailsView(data = data)
        }
    }
}