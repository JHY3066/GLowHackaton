package com.example.glowhackaton.network

import retrofit2.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class ItemNameRequest(val itemName: String)

data class SearchResponse(
    val success: Boolean,
    val data: List<String>?
)

interface ApiService {
    @GET("search")
    suspend fun search(@Query("query") query: String): Response<SearchResponse>

    @POST("sendItemName")
    fun  sendItemName(@Body itemNameRequest: ItemNameRequest): Call<Void>
}

