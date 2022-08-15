package com.salanevich.weatherapp.data.cache

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val KEY_DETAILS_ARGS = "details"

@HiltViewModel
class ArgumentsHolder @Inject constructor(): ViewModel() {

    private val bundle = Bundle()

    fun putArgument(key: String, value: Parcelable) {
        bundle.putParcelable(key, value)
    }

    fun <T: Parcelable> getArgument(key: String): Parcelable? {
        return bundle.getParcelable<T>(key)
    }

}