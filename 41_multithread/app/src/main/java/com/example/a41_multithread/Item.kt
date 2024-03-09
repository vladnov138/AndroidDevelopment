package com.example.a41_multithread

import android.media.Rating

data class Item(val id: Int, val title: String, val price: Float, val description: String,
    val category: String, val image: String, val rating: Rating
)

data class Rating(val rate: Float, val count: Int)
