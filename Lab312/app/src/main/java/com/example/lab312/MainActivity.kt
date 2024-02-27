package com.example.lab312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab312.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            binding.resultNumber.text =
                ((binding.firstNumber.text.toString()
                    .toIntOrNull() ?: 0) + (binding.secondNumber.text.toString().toIntOrNull() ?: 0)).toString()
        }
    }
}