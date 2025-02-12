package com.example.newsapp.ui

sealed class NewsIntent {
    data object LoadHeadlines : NewsIntent()
    data class LoadArticles(val query: String) : NewsIntent()
}