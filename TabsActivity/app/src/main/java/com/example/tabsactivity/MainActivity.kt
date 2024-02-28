package com.example.tabsactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tabsactivity.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.io.InputStreamReader

data class Tab(val title: String, val idx: Int, val rating: Int,)

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    class SlideAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
        var items: List<Movie> = emptyList()

        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): Fragment {
            return SlideFragment.newInstance(
                items[position].duration,
                items[position].country,
                items[position].year,
                items[position].rating
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movies: List<Movie> = parseMovies(R.raw.movies)
        val slideAdapter = SlideAdapter(this)
        slideAdapter.items = movies
        binding.viewPager2.adapter = slideAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = movies[position].title
        }.attach()
    }

    private fun parseMovies(id: Int): List<Movie> {
        val gson = Gson()
        val stream = resources.openRawResource(id)
        val reader = InputStreamReader(stream)
        return gson.fromJson(reader, Movies::class.java).movies.toList()
    }
}