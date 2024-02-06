package com.example.fragments_practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragments_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val frameLayout = viewBinding.fragmentContainerView
        val fm = supportFragmentManager
        fm.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragmentContainerView, TestFragment(), null)
            .commitAllowingStateLoss()
    }
}