package com.example.newsapp.data.domain

import com.example.newsapp.data.repository.SavedArticlesRepository
import com.example.newsapp.data.responses.Article
import javax.inject.Inject

class IsArticleSavedUseCase @Inject constructor(private val savedArticlesRepository: SavedArticlesRepository) {
    suspend fun invoke(article: Article) = savedArticlesRepository.isArticleSaved(article.url)
}