package com.example.a41_multithread

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.executeAsync
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {
    val _displayText = MutableLiveData<String>("")
    val displayText: LiveData<String> = _displayText

    init {
        viewModelScope.launch {
            val apiUrl = "https://fakestoreapi.com/products"
            fetchLoremIpsum(apiUrl)
        }
    }

    suspend fun fetchLoremIpsum(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        withContext(Dispatchers.IO) {
            try {
                client.newCall(request).executeAsync().use {
                    withContext(Dispatchers.Main) {
                        if (it.isSuccessful) {
                            val gson = Gson()
                            val arr = gson.fromJson(it.body.string(), Array<Item>::class.java)
                            _displayText.value = arr[0].title
                        } else {
                            _displayText.value = it.toString()
                        }
                    }
                }
            } catch (e: IOException) {
                Log.e("ViewModel", "test", e)
            }
        }
    }
}