package com.example.navigationdrawerpractice.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {
    private val _tabs = MutableLiveData<List<Tab>>()
    val tabs = _tabs

    private val folders = listOf("All", "Work", "Personal")

    init {
//        _tabs.value = List(100) {
//            Tab("Title: $it", it)
//        }
        _tabs.value = folders.mapIndexed { idx, title -> Tab(title, idx) }
    }
}

data class Tab(
    val title: String,
    val contentId: Int,
)