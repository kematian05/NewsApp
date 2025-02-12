package com.example.newsapp.api

import com.example.newsapp.data.domain.NewsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    fun getTopHeadlines(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsApi: NewsApi = retrofit.create(NewsApi::class.java)
}