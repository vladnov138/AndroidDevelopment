package com.example.workmanager

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class WeatherInteractor {
    companion object {
        const val TAG = "WeatherInteractor"
        const val API_URL = "https://api.openweathermap.org/data/2.5/weather"
        const val API_KEY = "fced5b9e382aa54d635c6d535be7046b" // Input your here
    }

    private val networkClient = NetworkClient()

    suspend fun getCurrentWeather(city: String): Map<String, String> {
        return withContext(Dispatchers.IO) {
            val result = networkClient.request(API_URL + "?q=$city&appid=$API_KEY")
            if (!result.isNullOrEmpty()) {
                parseWeather(result)
            } else {
                mapOf()
            }
        }
    }

    private fun parseWeather(jsonString: String): Map<String, String> {
        try {
            val json = JSONObject(jsonString)
            val main = json.getJSONObject("main")
            val temp = main.getString("temp")
            val humidity = main.getString("humidity")
            val pressure = main.getString("pressure")
            val weather = json.getJSONArray("weather").getJSONObject(0)
            val description = weather.getString("main")

            return mapOf(
                "temp" to temp,
                "humidity" to humidity,
                "pressure" to pressure,
                "description" to description
            )
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
        return mapOf()
    }
}