package com.example.randomfilmjson

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.randomfilmjson.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var movies: List<Movie>
    private var ptr = 0

    private lateinit var staticTvs: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        movies = parseMovies(R.raw.movies)
        staticTvs = arrayOf(viewBinding.staticTitleTv, viewBinding.staticDurationTv,
            viewBinding.staticCountryTv, viewBinding.staticYearTv, viewBinding.staticRatingTv)
        setStaticTvVisibility(View.INVISIBLE)
        initMovies()
        viewBinding.btnSelect.setOnClickListener(this::showMovie)
        viewBinding.btnRst.setOnClickListener(this::updateMovies)
    }

    private fun showMovie(v: View) {
        if (ptr >= movies.size) {
            viewBinding.errorTv.text = getText(R.string.err)
            return
        }
        if (viewBinding.staticTitleTv.visibility != View.VISIBLE) {
            setStaticTvVisibility(View.VISIBLE)
        }
        if (viewBinding.errorTv.text != "") {
            viewBinding.errorTv.text = ""
        }
        val movie = movies[ptr++]
        viewBinding.titleTv.text = movie.title
        viewBinding.durationTv.text = movie.duration
        viewBinding.countryTv.text = movie.country.joinToString(", ")
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
        setStaticTvVisibility(View.INVISIBLE)
        initMovies()
    }

    private fun initMovies() {
        viewBinding.errorTv.text = getText(R.string.start_msg)
        movies = movies.shuffled()
        ptr = 0
    }

    private fun setStaticTvVisibility(visibility: Int = View.INVISIBLE) {
        for (tv in staticTvs) {
            tv.visibility = visibility
        }
    }

    private fun parseMovies(id: Int): List<Movie> {
        val gson = Gson()
        val stream = resources.openRawResource(id)
        val reader = InputStreamReader(stream)
        return gson.fromJson(reader, Movies::class.java).movies.toList()
    }
}