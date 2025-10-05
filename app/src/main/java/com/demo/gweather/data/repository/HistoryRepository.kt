package com.demo.gweather.data.repository

import androidx.lifecycle.LiveData
import com.demo.gweather.data.local.WeatherDao
import com.demo.gweather.data.local.WeatherEntity

class HistoryRepository(private val dao: WeatherDao) {

    val history: LiveData<List<WeatherEntity>> = dao.getAllHistory()

    suspend fun insertWeather(weather: WeatherEntity) {
        dao.insertWeather(weather)
    }
}
