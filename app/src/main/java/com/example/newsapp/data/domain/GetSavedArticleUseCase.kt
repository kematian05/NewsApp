package com.example.newsapp.data.domain

import com.example.newsapp.data.repository.SavedArticlesRepository
import javax.inject.Inject

class GetSavedArticleUseCase @Inject constructor(private val savedArticlesRepository: SavedArticlesRepository) {
    suspend fun invoke() = savedArticlesRepository.getSavedArticles().sortedByDescending { it.savedAt }
}