package com.example.testretrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface StoreApi {
    @GET("/products")
    suspend fun getAllProducts(): List<Map<String, Any>>
}