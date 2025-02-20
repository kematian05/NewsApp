package com.example.newsapp.data.domain

import com.example.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun invoke(query: String) = newsRepository.getArticles(query).sortedByDescending { it.publishedAt }
}