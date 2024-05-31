package com.example.glowhackaton.network

import com.example.glowhackaton.model.ResponseAllMarket
import retrofit2.Response
import retrofit2.http.GET

interface MarketService {
    @GET("/market")
    suspend fun getMarket(): Response<ResponseAllMarket>
}