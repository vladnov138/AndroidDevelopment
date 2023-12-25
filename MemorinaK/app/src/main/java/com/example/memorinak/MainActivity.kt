package com.example.memorinak


import android.app.ActionBar
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val cardsImages = mutableListOf(
    R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4,
    R.drawable.i5, R.drawable.i6, R.drawable.i7, R.drawable.i8,
)
private val cardsImagesIndices = (1..8).flatMap { listOf(it, it) }.toTypedArray()
private var clickedSecond: ImageView? = null
private var clickedFirst: ImageView? = null
private var pairsFound: Int = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat() // единичный вес

        cardsImagesIndices.shuffle()
        val catViews = ArrayList<ImageView>()
        for (i in 1..16) {
            catViews.add( // вызываем конструктор для создания нового ImageView
                ImageView(applicationContext).apply {
                    setImageResource(R.drawable.blank)
                    layoutParams = params
                    tag = "i${cardsImagesIndices[i - 1]}"
                    setOnClickListener(colorListener)
                })
        }

        val rows = Array(4, { LinearLayout(applicationContext)})

        var count = 0
        for (view in catViews) {
            val row: Int = count / 4
            rows[row].addView(view)
            count ++
        }
        for (row in rows) {
            layout.addView(row)
        }
        setContentView(layout)
    }

    suspend fun setBackgroundWithDelay(v: ImageView) {
        val clickedIndex = v.tag.toString().filter { it.isDigit() }.toInt() - 1
        v.setImageResource(cardsImages[clickedIndex])
        delay(1000)

        if (clickedFirst == null) {
            clickedFirst = v
        } else {
            clickedSecond = v
        }

        when {
            clickedFirst != null && clickedSecond != null -> {
                if (clickedFirst!!.tag == clickedSecond!!.tag) {
                    clickedFirst!!.visibility = View.INVISIBLE
                    clickedSecond!!.visibility = View.INVISIBLE
                    clickedFirst = null
                    clickedSecond = null
                    pairsFound += 1

                    if (pairsFound == 8) {
                        Toast.makeText(this, "WIN!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    clickedFirst!!.setImageResource(R.drawable.blank)
                    clickedSecond!!.setImageResource(R.drawable.blank)
                    clickedFirst = null
                    clickedSecond = null
                }
            }
        }
//        v.setBackgroundColor(Color.YELLOW)
//        delay(1000)
//        v.visibility = View.INVISIBLE
//        v.isClickable = false
    }

    suspend fun openCards() {

    }

    // обработчик нажатия на кнопку
    val colorListener = View.OnClickListener() {
        // запуск функции в фоновом потоке
        GlobalScope.launch (Dispatchers.Main)
        { setBackgroundWithDelay(it as ImageView) }
    }
}