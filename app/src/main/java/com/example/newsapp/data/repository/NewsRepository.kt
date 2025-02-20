package com.example.newsapp.data.repository

import com.example.newsapp.api.NewsApi
import com.example.newsapp.data.responses.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getArticles(query: String): List<Article> {
        val response = newsApi.getArticles(query, "d44dd5bfd36449709d8ea5929718ad83")
        return response.articles
    }

}