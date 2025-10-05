package com.demo.gweather.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.demo.gweather.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = WeatherRepository()

    val errorLiveData = MutableLiveData<String?>()

}
