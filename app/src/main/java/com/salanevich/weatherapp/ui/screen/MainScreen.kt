package com.salanevich.weatherapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.salanevich.weatherapp.R
import com.salanevich.weatherapp.Screen
import com.salanevich.weatherapp.mvi.intent.MainIntent
import com.salanevich.weatherapp.mvi.state.MainState
import com.salanevich.weatherapp.ui.theme.WeatherAppTheme
import com.salanevich.weatherapp.vm.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val state = viewModel.stateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    when (val s = state.value) {
        is MainState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                 Text(stringResource(R.string.loading))
            }
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.reduce(MainIntent.LoadData, s)
                }
            }
        }
        is MainState.Loaded -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                LocationsList(modifier = modifier, locations = s.locations, { location ->
                    scope.launch {
                        viewModel.reduce(MainIntent.OpenForecast(location), s)
                    }
                }, { location ->
                    scope.launch {
                        viewModel.reduce(MainIntent.AddLocation(location), s)
                    }
                })
            }
        }
        is MainState.NavigatingToForecast -> {
            LaunchedEffect(Unit) {
                val dest = Screen.FORECAST.getNavDest(s.location)
                navController.navigate(dest)
            }
        }
    }
}

@Composable
fun LocationsList(
    modifier: Modifier = Modifier,
    locations: List<String>,
    onLocationClick: (String) -> Unit,
    onAddLocationClick: (String) -> Unit
) {
    Column(modifier = modifier
        .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        LazyColumn(modifier = modifier) {
            locations.forEach { location ->
                item(key = location) {
                    LocationItem(modifier, location) { onLocationClick(location.trim()) }
                }
            }
        }
        Row(modifier = modifier.padding(vertical = 16.dp)) {
            val location = remember { mutableStateOf("") }
            TextField(
                modifier = modifier
                    .width(200.dp)
                    .padding(end = 8.dp),
                singleLine = true,
                value = location.value, onValueChange = { location.value = it }
            )
            Button(
                onClick = { onAddLocationClick(location.value) },
                enabled = location.value.isNotBlank()
            ) {
                Text(text = "Add location")
            }
        }
    }
}

@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    location: String,
    onLocationClick: (String) -> Unit
) {
    Text(modifier = modifier.clickable { onLocationClick(location) },
        fontSize = 16.sp, text = location)
}

@Preview
@Composable
fun LocationsListPreview() {
    WeatherAppTheme {
        Surface {
            LocationsList(locations = listOf("New York", "Rome", "Paris"),
                onAddLocationClick =  {}, onLocationClick = {})
        }
    }
}