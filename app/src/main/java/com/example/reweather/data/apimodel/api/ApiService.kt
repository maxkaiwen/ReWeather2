package com.example.reweather.data.apimodel.api

import com.example.reweather.data.apimodel.model.WeatherBase
import retrofit2.http.GET
import retrofit2.http.Query

private const val appid:String="f0f2c5a52993cd6477bc6d78a348bd68"
//private const val apiWe:String="group?id=524901,703448,2643743&appid="
//private const val apiWe:String="weather?lat=$lat&lon=$lon&appid="
interface ApiService {
    @GET("weather")

    suspend fun getWeather(
            @Query("lat") key:String,@Query("lon") key2:String,@Query("appid") appid:String )
    : WeatherBase
}