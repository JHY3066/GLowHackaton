package com.example.glowhackaton

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun search(@Query("query") query: String): Response<SearchResponse>
}

data class SearchResponse(
    val success: Boolean,
    val data: List<String>?
)
