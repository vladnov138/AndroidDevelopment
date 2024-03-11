package com.example.fragmentweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.menu.R
import com.example.menu.databinding.FragmentDetailedWeatherBinding
import com.squareup.picasso.Picasso


class DetailedWeatherFragment : Fragment() {
    private var temperature: String? = null
    private var icon: String? = null
    private var city: String? = null
    private var feelsLike: String? = null
    private var pressure: String? = null
    private var windSpeed: String? = null


    private lateinit var viewBinding: FragmentDetailedWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            temperature = it.getString("temperature")
            icon = it.getString("icon")
            city = it.getString("city")
            feelsLike = it.getString("feels_like")
            pressure = it.getString("pressure")
            windSpeed = it.getString("wind_speed")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailedWeatherBinding.inflate(inflater, container, false)
        val imageUrl = "https://openweathermap.org/img/w/$icon.png"
        viewBinding.detailedTemperatureTextView.text = "${getString(R.string.temperature)}: $temperature"
        viewBinding.detailedPlaceTextView.text = "${getString(R.string.city)}: $city"
        Picasso.get().load(imageUrl).into(viewBinding.imageView)
        viewBinding.feelsLikeTextView.text = "${getString(R.string.feels_like)}: $feelsLike"
        viewBinding.pressureTextView.text = "${getString(R.string.pressure)}: $pressure"
        viewBinding.windSpeedTextView.text = "${getString(R.string.wind_speed)}: $windSpeed"
        return viewBinding.root
    }
}