package com.example.harrypotter.client

import com.example.harrypotter.api.HarryPotterAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: HarryPotterAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HarryPotterAPI::class.java)
    }
}