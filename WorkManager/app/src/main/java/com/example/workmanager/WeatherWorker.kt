package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class WeatherWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    val interactor = WeatherInteractor()

    override fun doWork(): Result {
        val city = inputData.getString("city")
        Log.d("mytag", city.toString())

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        val result = runBlocking {
            try {
                city?.let {
                    val result = interactor.getCurrentWeather(city)
                    Log.d("mytag", result.toString())

                    val outputData = Data.Builder()
                        .putString("temp", result["temp"])
                        .putString("humidity", result["humidity"])
                        .putString("pressure", result["pressure"])
                        .putString("weather", result["description"])
                        .build()

                    Log.d("mytag", "success $outputData")

                    // Возвращаем результат работы Worker
                    Result.success(outputData)
                } ?: run {
                    // Если city == null, возвращаем неудачу
                    Result.failure()
                }
            } catch (e: Exception) {
                Log.e("mytag", "Error fetching weather", e)
                Result.failure()
            }
        }

        Log.d("mytag", "work success ")
        return result
    }
}