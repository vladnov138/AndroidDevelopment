package com.example.menu

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentweather.Common.Common
import com.example.fragmentweather.DetailedWeatherFragment
import com.example.fragmentweather.Interface.RetrofitService
import com.example.menu.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherData: WeatherData
    private lateinit var mService: RetrofitService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mService = Common.retrofitService
        mService.getWeather().enqueue(object : Callback<WeatherData> {
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("Response", t.message.toString())
                Toast.makeText(this@MainActivity, "Ошибка с подключением к сети", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                Log.d("Response", response.body().toString())
                weatherData = response.body()!!
                loadDetailedFragment()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val config: Configuration = this.resources.configuration
        return when (item.itemId) {
            R.id.en -> {
                setLocale("en")
                true
            }
            R.id.ru -> {
                setLocale("ru")
                true
            }
            R.id.zh -> {
                setLocale("zh")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
        recreate()
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