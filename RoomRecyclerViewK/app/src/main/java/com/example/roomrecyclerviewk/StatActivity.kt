package com.example.roomrecyclerviewk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.roomrecyclerviewk.databinding.ActivityStatBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatActivity : AppCompatActivity() {
    private var _binding: ActivityStatBinding? = null
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
        _binding = ActivityStatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = db.resultsDao().getAll("RESULT DESC")
        data.observe(this) {
            GlobalScope.launch {
                val totalMoney = data.value?.sumOf { it.result ?: 0 } ?: 0
                val companiesCount = data.value?.size ?: 0
                val companiesAboveAvg = data.value?.count { it.result!! > totalMoney / companiesCount }
                val englishCompanies = data.value?.count { it.name?.any { c -> c.isLetter() && c !in 'А'..'я'} ?: false }
                val bestCompany = data.value?.maxByOrNull { it.result ?: 0 }?.name ?: ""
                val longNameCompany = data.value?.maxByOrNull { it.name?.length ?: 0 }?.name ?: ""
                withContext(Dispatchers.Main) {
                    binding.money.text = totalMoney.toString()
                    binding.good.text = companiesAboveAvg.toString()
                    binding.english.text = englishCompanies.toString()
                    binding.best.text = bestCompany
                    binding.longest.text = longNameCompany
                }
            }
        }
    }
}