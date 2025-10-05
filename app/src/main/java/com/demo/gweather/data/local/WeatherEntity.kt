package com.demo.gweather.data.local// WeatherEntity.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val country: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val sunrise: Long,
    val sunset: Long,
    val timestamp: Long = System.currentTimeMillis()
)
