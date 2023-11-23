package com.example.randomfilmjson

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.randomfilmjson.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var movies: List<Movie>
    private var ptr = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        movies = parseMovies(R.raw.movies)

        initMovies()
        viewBinding.btnSelect.setOnClickListener(this::showMovie)
        viewBinding.btnRst.setOnClickListener(this::updateMovies)
    }

    private fun showMovie(v: View) {
        if (ptr >= movies.size) {
            viewBinding.errorTv.text = resources.getText(R.string.err)
            return
        }
        val movie = movies[ptr++]
        viewBinding.titleTv.text = movie.title
        viewBinding.durationTv.text = movie.duration
        viewBinding.countryTv.text = movie.country
        viewBinding.yearTv.text = movie.year
        viewBinding.ratingTv.text = movie.rating
    }

    private fun updateMovies(v: View) {
        viewBinding.errorTv.text = ""
        viewBinding.titleTv.text = ""
        viewBinding.durationTv.text = ""
        viewBinding.countryTv.text = ""
        viewBinding.yearTv.text = ""
        viewBinding.ratingTv.text = ""
        initMovies()
    }

    private fun initMovies() {
        movies = movies.shuffled()
        ptr = 0
    }

    private fun parseMovies(id: Int): List<Movie> {
        val gson = Gson()
        val stream = resources.openRawResource(id)
        val reader = InputStreamReader(stream)
        return gson.fromJson(reader, Movies::class.java).movies.toList()
    }
}