package com.example.cryptoandroid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val usdRate = MutableLiveData<String>()
    val rateCheckInteractor = RateCheckInteractor()
    var refreshJob: kotlinx.coroutines.Job? = null

    fun onCreate() {
        refreshRate()
    }

    fun onRefreshClicked() {
//        refreshRate()
        startRateRefreshing()
    }

    private fun refreshRate() {
        GlobalScope.launch(Dispatchers.Main) {
            val rate = rateCheckInteractor.requestRate()
            Log.d(TAG, "usdRate = $rate")
            usdRate.value = rate
        }
    }

    private fun startRateRefreshing() {
        refreshJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                refreshRate()
                delay(5000)
            }
        }
    }

    private fun stopRateRefreshing() {
        refreshJob?.cancel()
    }

    companion object {
        const val TAG = "MainViewModel"
        const val USD_RATE_URL = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=RUB"
    }
}