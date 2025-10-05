package com.demo.gweather.data.repository


import com.demo.gweather.data.model.CurrentWeatherResponse
import com.demo.gweather.data.model.ReverseGeoResponse


class WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): CurrentWeatherResponse {
        return RetrofitInstance.weatherApi.getCurrentWeather(lat, lon, apiKey)
    }

    suspend fun getCityName(lat: Double, lon: Double, apiKey: String): ReverseGeoResponse? {
        return try {
            RetrofitInstance.geoApi.reverseGeocode(lat, lon, apiKey).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }



}
