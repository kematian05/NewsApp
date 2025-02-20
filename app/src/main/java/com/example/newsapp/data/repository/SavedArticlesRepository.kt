package com.example.newsapp.data.repository

import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.SavedArticle
import com.example.newsapp.db.SavedArticleDao
import com.example.newsapp.utils.toSavedArticle
import javax.inject.Inject

class SavedArticlesRepository @Inject constructor(private val savedArticleDao: SavedArticleDao) {

    suspend fun saveArticle(article: Article) {
        if (savedArticleDao.isArticleSaved(article.url) > 0) {
            deleteArticle(article.url)
        }
        else {
            savedArticleDao.saveArticle(article.toSavedArticle())
        }
    }

    private suspend fun deleteArticle(articleUrl: String) {
        savedArticleDao.deleteArticle(articleUrl)
    }

    suspend fun getSavedArticles(): List<SavedArticle> {
        return savedArticleDao.getSavedArticles()
    }

    suspend fun isArticleSaved(articleUrl: String): Boolean {
        return savedArticleDao.isArticleSaved(articleUrl) > 0
    }

}