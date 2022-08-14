package com.salanevich.weatherapp.data.store

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val KEY_LOCATIONS = "locations"
    }

    private val pref: SharedPreferences = context.getSharedPreferences("preferences", 0)
    private var locations: Set<String>? = null

    fun addLocation(location: String) {
        val locations = getLocations().toMutableSet().apply { add(location) }
        pref.edit().putStringSet(KEY_LOCATIONS, locations).apply()
        this.locations = locations
    }

    fun getLocations(): Set<String> {
        return if (locations == null || checkNotNull(locations).isEmpty()) {
            checkNotNull(pref.getStringSet(KEY_LOCATIONS, emptySet())).toSet()
        } else checkNotNull(locations)
    }

}