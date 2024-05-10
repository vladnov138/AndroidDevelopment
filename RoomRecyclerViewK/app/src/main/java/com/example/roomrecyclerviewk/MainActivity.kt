package com.example.roomrecyclerviewk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.roomrecyclerviewk.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "results.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            for (company in TestData.russianCompanies2020) {
                db.resultsDao().insert(company)
            }
        }

        binding.companiesList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        db.resultsDao().getAll("RESULT DESC").observe(this) {
            binding.companiesList.adapter = ResultAdapter(it.sortedByDescending { it.result })
        }

        binding.statistics.setOnClickListener {
            val intent = Intent(this, StatActivity::class.java)
            startActivity(intent)
        }

        binding.delete.setOnClickListener {
            val companyName = binding.toDelete.text.toString()
            GlobalScope.launch {
                db.resultsDao().deleteByName(companyName)
            }
            binding.toDelete.text.clear()
        }
    }
}