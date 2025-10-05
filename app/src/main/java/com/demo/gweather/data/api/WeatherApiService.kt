package com.demo.gweather.data.remote



import com.demo.gweather.data.model.CurrentWeatherResponse
import com.demo.gweather.data.model.ReverseGeoResponse
import retrofit2.http.GET
import retrofit2.http.Query



interface WeatherApiService {
    // Current Weather API (FREE tier)
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse

    // Reverse geocoding to get city name from coordinates
    @GET("geo/1.0/reverse")
    suspend fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int = 1
    ): List<ReverseGeoResponse>
}