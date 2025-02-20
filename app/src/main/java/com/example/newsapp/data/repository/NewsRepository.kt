package com.example.newsapp.data.repository

import com.example.newsapp.api.NewsApi
import com.example.newsapp.data.domain.NetworkModule
import com.example.newsapp.data.responses.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getArticles(query: String): List<Article> {
        val response = newsApi.getArticles(query, "215629b8cdd24d6f8e5beeeef66c1dc3")
        return response.articles
    }

}