package com.example.tabsactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.tabsactivity.databinding.FragmentSlideBinding
import java.time.Duration

class SlideFragment : Fragment() {

    private lateinit var binding: FragmentSlideBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.durationTv.text = arguments?.getString(DURATION)
        binding.countryTv.text = arguments?.getString(COUNTRY)
        binding.yearTv.text = arguments?.getString(YEAR)
        binding.ratingTv.text = arguments?.getString(RATING)
    }

    companion object {
        private const val DURATION = "duration"
        private const val COUNTRY = "country"
        private const val YEAR = "year"
        private const val RATING = "rating"

        @JvmStatic
        fun newInstance(duration: String, country: List<String>, year: String, rating: String) =
            SlideFragment().apply {
                arguments = bundleOf(
                    DURATION to duration,
                    COUNTRY to country,
                    YEAR to year,
                    RATING to rating
                )
            }
    }
}