package com.example.reweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.reweather.data.apimodel.repo.MainRepository
import com.example.reweather.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {


    fun getWeather(lat: String, lon: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getWeather(lat,lon)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "error Occured"))

        }


    }
}