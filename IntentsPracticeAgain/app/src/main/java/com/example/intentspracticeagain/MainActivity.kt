package com.example.intentspracticeagain

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intentspracticeagain.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1234567890"))
        startActivity(intent)
//        binding.button.setOnClickListener {
//            val sendIntent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, binding.editTextText.text)
//                type = "text/plain"
//            }
//
//            try {
//                startActivity(sendIntent)
//            } catch (e: ActivityNotFoundException) {
//                // Ignore
//            }
//        }
    }
}