package com.example.portraitlandscapepresentk2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import com.example.portraitlandscapepresentk2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewBinder: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var cars: Map<String, Int>
    private lateinit var selectedCar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        val carsArr = resources.getStringArray(R.array.pictures)
        cars = mapOf(
            carsArr[0] to R.drawable.car1,
            carsArr[1] to R.drawable.car2,
            carsArr[2] to R.drawable.car3
        )
        selectedCar = carsArr[0]
        selectedCar = savedInstanceState?.getString("selectedCar", selectedCar) ?: selectedCar
        cars[selectedCar]?.let { viewBinder.picture.setImageResource(it) }
        adapter = ArrayAdapter.createFromResource(this, R.array.pictures, R.layout.item)
        val spinner = viewBinder.picturesList
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("selectedCar", selectedCar)
    }

    fun onChangePictureClick(v: View) {
        cars[selectedCar]?.let { viewBinder.picture.setImageResource(it) }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this, "выбран элемент $position", Toast.LENGTH_SHORT ).show()
        selectedCar = parent?.selectedItem.toString()
        Log.d("sel", selectedCar)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "не выбран элемент", Toast.LENGTH_SHORT ).show()
    }
}