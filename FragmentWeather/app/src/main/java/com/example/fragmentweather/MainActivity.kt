package com.example.fragmentweather

import WeatherData
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentweather.Common.Common
import com.example.fragmentweather.Interface.RetrofitService
import com.example.fragmentweather.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var mService: RetrofitService
    private lateinit var weatherData: WeatherData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        mService = Common.retrofitService
        mService.getWeather().enqueue(object : Callback<WeatherData> {
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("Response", t.message.toString())
            }

            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                Log.d("Response", response.body().toString())
                weatherData = response.body()!!
                loadShortFragment()
            }
        })

        viewBinding.liteButton.setOnClickListener {
            loadShortFragment()
        }
        viewBinding.detailedButton.setOnClickListener {
            loadDetailedFragment()
        }
    }

    fun loadShortFragment() {
        val fm = supportFragmentManager
        fm.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.weatherFrameLayout, ShortWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString("temperature", weatherData.main.feels_like.toInt().toString())
                    putString("icon", weatherData.weather[0].icon)
                    putString("city", "Moscow")
                }
            }, null)
            .commitAllowingStateLoss()
    }

    fun loadDetailedFragment() {
        val fm = supportFragmentManager
        fm.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.weatherFrameLayout, DetailedWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString("temperature", weatherData.main.feels_like.toInt().toString())
                    putString("icon", weatherData.weather[0].icon)
                    putString("city", "Moscow")
                    putString("feels_like", weatherData.main.feels_like.toInt().toString())
                    putString("pressure", weatherData.main.pressure.toString())
                    putString("wind_speed", weatherData.wind.speed.toString())
                }
            }, null)
            .commitAllowingStateLoss()
    }
}