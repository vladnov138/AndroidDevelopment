package com.example.sensorslist

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sensorslist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val adapter = ArrayAdapter.createFromResource(this, R.array.sensors, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> showSensorsByCategory(sensorManager, position)
                    1 -> showSensorsByCategory(sensorManager, position)
                    2 -> showSensorsByCategory(sensorManager, position)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })
        showSensorsByCategory(sensorManager, 0)
    }

    private fun showSensorsByCategory(sensorManager: SensorManager, sensorType: Int) {
        val sensorNames = when (sensorType) {
            0 -> listOf(
                Sensor.TYPE_HEART_RATE,
                Sensor.TYPE_HEART_BEAT,
                Sensor.TYPE_DEVICE_PRIVATE_BASE
            )
            1 -> listOf(
                Sensor.TYPE_MAGNETIC_FIELD,
                Sensor.TYPE_LIGHT,
                Sensor.TYPE_PRESSURE,
                Sensor.TYPE_RELATIVE_HUMIDITY,
                Sensor.TYPE_AMBIENT_TEMPERATURE
            )
            else -> listOf(
                Sensor.TYPE_ACCELEROMETER,
                Sensor.TYPE_GYROSCOPE,
                Sensor.TYPE_PROXIMITY,
                Sensor.TYPE_GRAVITY,
                Sensor.TYPE_LINEAR_ACCELERATION,
                Sensor.TYPE_ROTATION_VECTOR,
                Sensor.TYPE_GAME_ROTATION_VECTOR,
                Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
                Sensor.TYPE_SIGNIFICANT_MOTION,
                Sensor.TYPE_STEP_DETECTOR,
                Sensor.TYPE_STEP_COUNTER,
                Sensor.TYPE_MOTION_DETECT
            )
        }
        val sensors = mutableListOf<Sensor>()
        for (sensorName in sensorNames) {
            val sensor = sensorManager.getDefaultSensor(sensorName)
            sensor?.let { sensors.add(it) }
        }
        binding.sensorsList.adapter = SensorAdapter(sensors)
    }
}