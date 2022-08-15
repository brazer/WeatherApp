package com.salanevich.weatherapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salanevich.weatherapp.data.cache.ArgumentsHolder
import com.salanevich.weatherapp.data.cache.KEY_DETAILS_ARGS
import com.salanevich.weatherapp.ui.model.ForecastItemDetailsModel
import com.salanevich.weatherapp.ui.screen.DetailsScreen
import com.salanevich.weatherapp.ui.screen.ForecastScreen
import com.salanevich.weatherapp.ui.screen.MainScreen
import com.salanevich.weatherapp.vm.ForecastViewModel
import com.salanevich.weatherapp.vm.MainViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val args = hiltViewModel<ArgumentsHolder>()
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
                val viewModel = hiltViewModel<ForecastViewModel>()
                ForecastScreen(navController = navController, location = location, viewModel = viewModel, arguments = args)
            }
            composable(
                Screen.DETAILS.getDestination(),
                arguments = listOf(navArgument(Screen.DETAILS.argument!!) { NavType.StringType })
            ) { backStackEntry ->
                val data = checkNotNull(args.getArgument<ForecastItemDetailsModel>(KEY_DETAILS_ARGS)) as ForecastItemDetailsModel
                val day = checkNotNull(backStackEntry.arguments?.getString(Screen.DETAILS.argument))
                DetailsScreen(day = day, data = data)
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