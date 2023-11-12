package com.example.practicepatterns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)
        viewModel.counter.observe(this) {
            textView.text = it.toString()
            textView.textSize = it.toFloat()
        }

        button.setOnClickListener {
            viewModel.incrementCounter()
        }

        button.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setTitle("Ты чево наделал")
                .setMessage("Закрой, дурачок, научись приложением пользоваться")
                .setPositiveButton("Yes") { dialog, which ->
                    Toast.makeText(this, "Bla bla bla", Toast.LENGTH_LONG).show()
                    Snackbar.make(it, "Hoba", Snackbar.LENGTH_LONG).show()
                }
                .show()
            true
        }
    }
}