package com.demo.gweather.ui.history// HistoryViewModel.kt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.demo.gweather.data.local.WeatherDatabase
import com.demo.gweather.data.local.WeatherEntity
import com.demo.gweather.data.repository.HistoryRepository
import com.demo.gweather.data.repository.WeatherRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = WeatherDatabase.getDatabase(application).weatherDao()
    private val repository = HistoryRepository(dao)

    val history: LiveData<List<WeatherEntity>> = repository.history
}
