package com.demo.gweather.ui.current

import android.app.Application
import androidx.lifecycle.*
import com.demo.gweather.data.repository.WeatherRepository
import kotlinx.coroutines.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.gweather.data.local.WeatherDatabase
import com.demo.gweather.data.local.WeatherEntity
import com.demo.gweather.data.model.CurrentWeatherResponse

import com.demo.gweather.data.model.ReverseGeoResponse
import com.demo.gweather.data.repository.HistoryRepository

class CurrentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val weatherRepo = WeatherRepository()

    val weatherData = MutableLiveData<CurrentWeatherResponse?>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun fetchWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = weatherRepo.getWeather(lat, lon, apiKey)
                weatherData.value = response
            } catch (e: Exception) {
                error.value = e.localizedMessage
            } finally {
                loading.value = false
            }
        }
    }
}