package com.example.newsapp.viewModels

import com.example.newsapp.data.responses.SavedArticle

data class SavedState(
    val savedArticles: List<SavedArticle> = emptyList()
)