package com.example.reweather.data.apimodel.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val appid: String = "f0f2c5a52993cd6477bc6d78a348bd68"

    // private val apiWe:String="group?id=524901,703448,2643743&appid="
    // private val apiWe:String="bbox=12,32,15,37,10&appid="
    private val TAG = "INFO: "

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}