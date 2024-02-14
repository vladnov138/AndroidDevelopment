package com.example.fragmentweather.Interface

import WeatherData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("weather?q=moscow&appid=fced5b9e382aa54d635c6d535be7046b&units=metric")
    fun getWeather(): Call<WeatherData>
}