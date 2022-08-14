package com.salanevich.weatherapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salanevich.weatherapp.ui.model.ForecastItemDetailsModel
import com.salanevich.weatherapp.ui.model.ForecastItemModel
import com.salanevich.weatherapp.ui.screen.DetailsScreen
import com.salanevich.weatherapp.ui.screen.ForecastScreen
import com.salanevich.weatherapp.ui.screen.MainScreen
import com.salanevich.weatherapp.vm.MainViewModel
import java.util.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Box(Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Screen.MAIN.getDestination()) {
            composable(Screen.MAIN.getDestination()) {
                val viewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                Screen.FORECAST.getDestination(),
                arguments = listOf(navArgument(Screen.FORECAST.argument!!) { NavType.StringType })
            ) { backStackEntry ->
                val location = checkNotNull(backStackEntry.arguments?.getString(Screen.FORECAST.argument))
                ForecastScreen(navController = navController, location = location)
            }
            composable(
                Screen.DETAILS.getDestination(),
                arguments = listOf(navArgument(Screen.DETAILS.argument!!) { NavType.StringType })
            ) { backStackEntry ->
                val day = checkNotNull(backStackEntry.arguments?.getString(Screen.DETAILS.argument))
                DetailsScreen(day = day)
            }
        }
    }
}

enum class Screen(private val destination: String, val argument: String?) {
    MAIN("main", null),
    FORECAST("forecast", "location"),
    DETAILS("details", "day");

    fun getNavDest(argument: String): String {
        return "$destination/$argument"
    }

    fun getDestination(): String {
        return if (argument != null) {
            "$destination/{$argument}"
        } else destination
    }
}