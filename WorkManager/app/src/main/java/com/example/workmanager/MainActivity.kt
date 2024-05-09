package com.example.workmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.workmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startWork()
        binding.refreshBtn.setOnClickListener {
            startWork()
        }
    }

    private fun startWork() {
        val dataIrkutsk = workDataOf("city" to "Irkutsk")
        val dataMoscow = workDataOf("city" to "Moscow")
        val dataKrasnoyarsk = workDataOf("city" to "Krasnoyarsk")
        val workRequestIrkutsk = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
            .setInputData(dataIrkutsk)
            .build()
        val workRequestMoscow = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
            .setInputData(dataMoscow)
            .build()
        val workRequestKrasnoyarsk = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
            .setInputData(dataKrasnoyarsk)
            .build()

        WorkManager
            .getInstance(this)
            .beginWith(workRequestIrkutsk)
            .then(workRequestMoscow)
            .then(workRequestKrasnoyarsk)
            .enqueue()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestIrkutsk.id).observe(this) {
            if (it != null && it.state == WorkInfo.State.SUCCEEDED) {
                val data = it.outputData
                binding.tempIrkutsk.text = data.getString("temp")
                binding.pressureIrkutsk.text = data.getString("pressure")
                binding.humidityIrkutsk.text = data.getString("humidity")
                binding.weatherIrkutsk.text = data.getString("weather")
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestMoscow.id).observe(this) {
            if (it != null && it.state == WorkInfo.State.SUCCEEDED) {
                val data = it.outputData
                binding.tempMoscow.text = data.getString("temp")
                binding.pressureMoscow.text = data.getString("pressure")
                binding.humidityMoscow.text = data.getString("humidity")
                binding.weatherMoscow.text = data.getString("weather")
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestKrasnoyarsk.id).observe(this) {
            if (it != null && it.state == WorkInfo.State.SUCCEEDED) {
                val data = it.outputData
                binding.tempKrasnoyarsk.text = data.getString("temp")
                binding.pressureKrasnoyarsk.text = data.getString("pressure")
                binding.humidityKrasnoyarsk.text = data.getString("humidity")
                binding.weatherKrasnoyarsk.text = data.getString("weather")
            }
        }
    }
}