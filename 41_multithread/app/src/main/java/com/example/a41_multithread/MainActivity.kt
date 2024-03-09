package com.example.a41_multithread

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.example.a41_multithread.databinding.ActivityMainBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        thread {
//            var i = 0
//            while (true) {
//                i++
//                Thread.sleep(1000)
//                runOnUiThread {
//                    binding.counterText.text = "$i seconds"
//                }
//            }
//        }

//        var isLeftLegStepped = false
//        var lock = Any()
//
//        thread(name = "left-leg") {
//            while (true) {
//                synchronized(lock) {
//                    if (!isLeftLegStepped) {
//                        printLeg()
//                        isLeftLegStepped = true
//                    }
//                }
//            }
//        }
//
//        thread(name = "right-leg") {
//            while (true) {
//                synchronized(lock) {
//                    if (isLeftLegStepped) {
//                        printLeg()
//                        isLeftLegStepped = false
//                    }
//                }
//            }
//        }

        viewModel.displayText.observe(this) {
            binding.counterText.text = it
        }
    }

    private fun printLeg() {
        Log.d("LEG", "${Thread.currentThread().name} step")
    }
}