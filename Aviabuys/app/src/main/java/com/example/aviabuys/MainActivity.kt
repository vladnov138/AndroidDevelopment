package com.example.aviabuys

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var cities: MutableList<String>
    private lateinit var submit: Button
    private lateinit var autoCompleteTv: AutoCompleteTextView
    private lateinit var autoCompleteFromTv: AutoCompleteTextView
    private lateinit var departureDateEt: EditText
    private lateinit var arrivingDateEt: EditText

    private lateinit var departureCity: String
    private lateinit var arrivingCity: String

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openDatePicker(dateEt: EditText) {
        val curTime = LocalDate.now()
        val year = curTime.year
        val month = curTime.monthValue
        val day = curTime.dayOfMonth

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