package com.example.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent(this, TimeService::class.java)
        intent.putExtra("a", "42")
        startService(intent)
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val timeServiceBinder = service as? TimeService.TimeServiceBinder ?: return
                val time = timeServiceBinder.getTime()

                timeServiceBinder.sendMessage()
                Log.e("ServiceConnection", "onServiceConnected time=$time")
                timeServiceBinder.getMessageSendResults()
                    .asLiveData()
                    .observe(this@MainActivity) {
                        Log.e("ServiceConnection", "onServiceConnected it=$it")
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e("ServiceConnection", "onServiceDisconnected")
            }
        }
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
}