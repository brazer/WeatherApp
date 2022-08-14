package com.salanevich.weatherapp.utils

import android.content.res.Resources
import com.salanevich.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

val commonDateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

fun getDate(date: Date, position: Int, resources: Resources): String {
    return when (position) {
        0 -> resources.getString(R.string.today)
        1 -> resources.getString(R.string.tomorrow)
        else -> commonDateFormatter.format(date)
    }
}