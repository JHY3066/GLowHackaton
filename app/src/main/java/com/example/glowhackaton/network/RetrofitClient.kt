package com.example.glowhackaton.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://localhost:8080")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val main = retrofit.create(Main::class.java)