package com.example.newsapp.data.domain

import com.example.newsapp.data.repository.SavedArticlesRepository
import com.example.newsapp.data.responses.SavedArticle
import javax.inject.Inject

class GetSavedArticleUseCase @Inject constructor(private val savedArticlesRepository: SavedArticlesRepository) {
    suspend fun invoke(
        onSuccess: (List<SavedArticle>) -> Unit,
    ) {
        onSuccess(savedArticlesRepository.getSavedArticles().sortedByDescending { it.savedAt })
    }
}