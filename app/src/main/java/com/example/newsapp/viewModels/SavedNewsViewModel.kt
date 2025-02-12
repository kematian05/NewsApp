package com.example.newsapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.domain.Article
import com.example.newsapp.data.domain.SavedArticle
import com.example.newsapp.db.SavedArticleDatabase
import com.example.newsapp.utils.toSavedArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SavedNewsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SavedArticleDatabase.getDatabase(application).savedArticleDao()
    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            if (_isSaved.value) {
                db.deleteArticle(article.url)
            } else {
                db.saveArticle(article.toSavedArticle())
            }
            _isSaved.value = !_isSaved.value
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteArticle(article.url)
        }
    }

    suspend fun getSavedArticles(): List<SavedArticle> {
        return db.getSavedArticles()
    }

    fun isArticleSaved(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            _isSaved.value = db.isArticleSaved(article.url) > 0
        }
    }
}
