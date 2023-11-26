package com.example.colorgame

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable


class MainActivity : AppCompatActivity() {

    private lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tiles = arrayOf(
            arrayOf(
                findViewById(R.id.t00),
                findViewById(R.id.t01),
                findViewById(R.id.t02),
                findViewById(R.id.t03)
            ), arrayOf(
                findViewById(R.id.t10),
                findViewById(R.id.t11),
                findViewById(R.id.t12),
                findViewById(R.id.t13)
            ), arrayOf(
                findViewById(R.id.t20),
                findViewById(R.id.t21),
                findViewById(R.id.t22),
                findViewById(R.id.t23)
            ), arrayOf(
                findViewById(R.id.t30),
                findViewById(R.id.t31),
                findViewById(R.id.t32),
                findViewById(R.id.t33)
            )
        )
    }

    private fun getCoordFromString(s: String): Coord {
        Log.d("Coords", "Coords: ${s[0]} ${s[1]}")
        return Coord(s[0] - '0', s[1] - '0')
    }

    private fun changeColor(view: View) {
        val brightColor = getColor(R.color.bright)
        val darkColor = getColor(R.color.dark)
        val drawable = view.background as ColorDrawable
        if (drawable.color == brightColor) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.tag.toString())
        changeColor(v)

        for (i in 0..3) {
            changeColor(tiles[coord.x][i])
            changeColor(tiles[i][coord.y])
        }
        checkVictory()
    }

    private fun checkVictory() {
        var k = 0
        for (row in tiles) {
            for (item in row) {
                val backgroundColor = (item.background as? ColorDrawable)?.color
                val targetColor = getColor(R.color.dark)
                if (backgroundColor == targetColor) {
                    k++
                }
            }
        }
        if (k == 0 || k == 16) {
            Toast.makeText(this, "WIN!!!", Toast.LENGTH_LONG).show()
        }
    }


}