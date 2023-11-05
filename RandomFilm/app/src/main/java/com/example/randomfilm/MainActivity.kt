package com.example.randomfilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var movies: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        movies = getFilms()
    }

    fun selectRandomFilm(v: View) {
        textView.text = if (movies.isEmpty()) {
            "All films watched"
        } else {
            val idx = Random.nextInt(movies.size)
            val movie = movies[idx]
            movies.removeAt(idx)
            movie
        }
    }

    fun resetFilms(v: View) {
        movies = getFilms()
        textView.text = getString(R.string.initial_text)
    }

    private fun getFilms(): MutableList<String> =
        resources.getStringArray(R.array.movies).toMutableList()
}