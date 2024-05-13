package com.example.broadcastsub

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcastsub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var isWaiting = true
    private var waitingTime = 0
    private val toastText = "Ждун больше не ждет!"

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

        startWaiting()

        binding.button.setOnClickListener {
            if (isWaiting) {
                stopWaiting()
                binding.button.text = "Надоело не ждать?"
            } else {
                startWaiting()
                binding.button.text = "Надоело ждать?"
            }
        }
    }

    private val timeTickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (isWaiting) {
                waitingTime++
                binding.textView.text = "Время созерцания: $waitingTime минут"
            }
        }
    }

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent?.getIntExtra("level", 0)
            val scale = intent?.getIntExtra("scale", 0)
            val batteryPercents = level?.times(100)?.div(scale!!) ?: -1
            if (batteryPercents < 15) {
                isWaiting = false
                binding.textView.text = "Накормите Ждуна, силы на исходе!"
            } else {
                isWaiting = true
                waitingTime = 0
                binding.textView.text = "Время созерцания: $waitingTime минут"
            }
        }
    }

    private fun startWaiting() {
        registerReceiver(timeTickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        isWaiting = true
        waitingTime = 0
        binding.textView.text = "Время созерцания: $waitingTime минут"
    }

    fun stopWaiting() {
        isWaiting = false
        unregisterReceiver(timeTickReceiver)
        unregisterReceiver(batteryReceiver)
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        binding.textView.text = "Ждун больше не ждёт!"
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }
}