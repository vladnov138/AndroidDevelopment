package com.example.fragmentweather.Common

import com.example.fragmentweather.Interface.RetrofitService
import com.example.fragmentweather.Retrofit.RetrofitClient

object Common {
    private val BASE_URL =
        "https://api.openweathermap.org/data/2.5/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}