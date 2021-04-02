package com.example.reweather.data.apimodel.api
private const val appid:String="f0f2c5a52993cd6477bc6d78a348bd68"
class ApiHelper(private val apiService: ApiService) {




    suspend fun getWeather(lat: String, lon: String) = apiService.getWeather(lat, lon, appid)

}