package com.example.newsapp.api

import com.example.newsapp.data.responses.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}