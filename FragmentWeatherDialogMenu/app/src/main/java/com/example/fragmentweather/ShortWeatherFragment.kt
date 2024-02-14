package com.example.fragmentweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentweather.databinding.FragmentShortWeatherBinding
import com.squareup.picasso.Picasso


class ShortWeatherFragment : Fragment() {
    private var temperature: String? = null
    private var icon: String? = null
    private var city: String? = null

    private lateinit var viewBinding: FragmentShortWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            temperature = it.getString("temperature")
            icon = it.getString("icon")
            city = it.getString("city")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentShortWeatherBinding.inflate(inflater, container, false)
        val imageUrl = "https://openweathermap.org/img/w/$icon.png"
        viewBinding.temperatureTextView.text = temperature
        viewBinding.placeTextView.text = city
        Picasso.get().load(imageUrl).into(viewBinding.iconImageView)
        return viewBinding.root
    }
}