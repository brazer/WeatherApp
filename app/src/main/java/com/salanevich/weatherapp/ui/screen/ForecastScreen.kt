package com.salanevich.weatherapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.salanevich.weatherapp.R
import com.salanevich.weatherapp.Screen
import com.salanevich.weatherapp.data.cache.ArgumentsHolder
import com.salanevich.weatherapp.data.cache.KEY_DETAILS_ARGS
import com.salanevich.weatherapp.mvi.intent.ForecastIntent
import com.salanevich.weatherapp.mvi.state.ForecastState
import com.salanevich.weatherapp.ui.model.ForecastItemModel
import com.salanevich.weatherapp.ui.theme.Typography
import com.salanevich.weatherapp.ui.theme.WeatherAppTheme
import com.salanevich.weatherapp.vm.ForecastViewModel
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ForecastScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    location: String,
    viewModel: ForecastViewModel,
    arguments: ArgumentsHolder
) {
    val state = viewModel.stateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    when (val s = state.value) {
        is ForecastState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.loading))
            }
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.reduce(ForecastIntent.Load(location), s)
                }
            }
        }
        is ForecastState.EmptyData -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.empty_data))
            }
        }
        is ForecastState.LoadedData -> {
            ForecastList(modifier.fillMaxSize(), data = s.list, location = location) { position ->
                scope.launch {
                    viewModel.reduce(ForecastIntent.NavigateToDetails(position), s)
                }
            }
        }
        is ForecastState.NavigatingToDetails -> {
            LaunchedEffect(Unit) {
                arguments.putArgument(KEY_DETAILS_ARGS, s.item)
                navController.navigate(Screen.DETAILS.getNavDest(s.item.date)) {
                    popUpTo(Screen.FORECAST.getNavDest(location))
                }
                scope.launch {
                    viewModel.reduce(ForecastIntent.Load(location), s)
                }
            }
        }
    }
}

@Composable
fun ForecastList(
    modifier: Modifier = Modifier,
    location: String,
    data: List<ForecastItemModel>,
    onItemClick: (Int) -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), Alignment.Center) {
        Text(
            text = location,
            fontStyle = Typography.h1.fontStyle,
            modifier = modifier
                .fillMaxWidth().padding(top = 8.dp)
                .align(Alignment.TopCenter),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            data.forEachIndexed { index, forecastItemModel ->
                item(key = forecastItemModel.date) {
                    ForecastItem(item = forecastItemModel, onClick = { onItemClick(index) })
                }
            }
        }
    }
}

@Composable
fun ForecastItem(
    modifier: Modifier = Modifier,
    item: ForecastItemModel,
    onClick: () -> Unit
) {
    Row(modifier = modifier.clickable { onClick() }) {
        AsyncImage(
            model = item.weatherIconUrl,
            contentDescription = "weather icon",
            modifier = modifier.width(30.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = item.date, fontSize = 22.sp,
            modifier = modifier.padding(horizontal = 8.dp)
        )
        item.temperature?.let { Text(it, fontSize = 22.sp) }
    }
}

@Preview
@Composable
fun ForecastListPreview() {
    val list = ArrayList<ForecastItemModel>()
    repeat(5) {
        val item = ForecastItemModel(it.toString(), "32°C", "https://cdn.weatherapi.com/weather/64x64/day/113.png")
        list.add(item)
    }
    WeatherAppTheme {
        Surface {
            ForecastList(data = list, location = "Minsk", onItemClick = {})
        }
    }
}