package com.example.newsapp.viewModels

import com.example.newsapp.data.responses.Article

data class HomeState(
    val isLoading: Boolean = false,
    val isLoadingSearch: Boolean = false,
    val headlines: List<Article> = emptyList(),
    val initialArticles: List<Article> = emptyList(),
    val filteredArticles: List<Article> = emptyList(),
    val searchQuery: String = "",
    val error: String = ""
)