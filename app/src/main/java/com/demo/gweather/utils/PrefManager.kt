package com.demo.gweather.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    val prefs: SharedPreferences =
        context.getSharedPreferences("gweather", Context.MODE_PRIVATE)

    fun saveLogin(username: String) {
        prefs.edit().putString("username", username).putBoolean("logged_in", true).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("logged_in", false)

    fun saveLocation(lat: Double, lon: Double) {
        prefs.edit().putFloat("lat", lat.toFloat()).putFloat("lon", lon.toFloat()).apply()
    }

    fun getLatitude(): Double = prefs.getFloat("lat", 0f).toDouble()
    fun getLongitude(): Double = prefs.getFloat("lon", 0f).toDouble()

    fun saveWeatherData(temp: Double, sunrise: Long, sunset: Long, desc: String) {
        prefs.edit()
            .putFloat("temp", temp.toFloat())
            .putLong("sunrise", sunrise)
            .putLong("sunset", sunset)
            .putString("desc", desc)
            .apply()
    }

    fun getWeatherDesc(): String = prefs.getString("desc", "") ?: ""
}
