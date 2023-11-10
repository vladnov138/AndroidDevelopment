package com.example.aviabuys

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var cities: MutableList<String>
    private lateinit var submit: Button
    private lateinit var autoCompleteTv: AutoCompleteTextView
    private lateinit var autoCompleteFromTv: AutoCompleteTextView
    private lateinit var departureDateEt: EditText
    private lateinit var arrivingDateEt: EditText

    private lateinit var departureCity: String
    private lateinit var arrivingCity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cities = resources.getStringArray(R.array.cities).toMutableList()
        autoCompleteTv = findViewById(R.id.auto_complete_to_city_tv)
        val adapter = ArrayAdapter<String>(this, R.layout.list_items, cities)
        autoCompleteTv.setAdapter(adapter)

        autoCompleteFromTv = findViewById(R.id.auto_complete_from_city_tv)
        val adapterFrom = ArrayAdapter<String>(this, R.layout.list_items, cities)
        autoCompleteFromTv.setAdapter(adapterFrom)

        departureDateEt = findViewById(R.id.departureDate)
        departureDateEt.setOnClickListener {
            openDatePicker(departureDateEt)
        }

        arrivingDateEt = findViewById(R.id.arrivingDate)
        arrivingDateEt.setOnClickListener {
            openDatePicker(arrivingDateEt)
        }
    }

    private fun openDatePicker(dateEt: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            this,
            { view, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay.${selectedMonth + 1}.$selectedYear"
                dateEt.setText(date)
            }, year, month, day
        )

        datePickerDialog.show()
    }
}