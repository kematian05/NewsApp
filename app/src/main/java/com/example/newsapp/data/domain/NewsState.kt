package com.example.newsapp.data.domain

data class NewsState(
    val headlines: List<Article> = emptyList(),
    val articles: List<Article> = emptyList(),
    val filteredArticles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)