package com.example.reweather.data.apimodel.repo

import com.example.reweather.data.apimodel.api.ApiHelper



class MainRepository(private val apiHelper: ApiHelper) {
suspend fun getWeather(lat: String, lon: String) =apiHelper.getWeather(lat,lon)


}