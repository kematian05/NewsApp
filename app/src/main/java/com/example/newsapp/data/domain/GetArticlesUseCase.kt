package com.example.newsapp.data.domain

import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.responses.Article
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun invoke(
        query: String,
        onLoading: () -> Unit,
        onSuccess: (List<Article>) -> Unit,
        onError: (String) -> Unit,
        onFinally: () -> Unit
    ) {
        onLoading()
        try {
            val articles = newsRepository.getArticles(query).sortedByDescending { it.publishedAt }
            onSuccess(articles)
        } catch (e: Exception) {
            onError("Error fetching articles")
        } finally {
            onFinally()
        }
    }
}