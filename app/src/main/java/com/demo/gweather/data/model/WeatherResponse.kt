package com.demo.gweather.data.model

data class CurrentWeatherResponse(
    val coord: Coord,
    val weather: List<WeatherCondition>,
    val main: Main,
    val sys: Sys,
    val name: String,
    val dt: Long
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

// For getting city name from coordinates (optional)
data class ReverseGeoResponse(
    val name: String,
    val country: String,
    val state: String?
)