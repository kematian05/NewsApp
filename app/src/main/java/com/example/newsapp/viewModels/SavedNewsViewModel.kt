package com.example.newsapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.domain.GetSavedArticleUseCase
import com.example.newsapp.data.domain.IsArticleSavedUseCase
import com.example.newsapp.data.domain.SaveArticleUseCase
import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.SavedArticle
import com.example.newsapp.db.SavedArticleDatabase
import com.example.newsapp.utils.toSavedArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsViewModel @Inject constructor(
    private val getSavedArticleUseCase: GetSavedArticleUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val isArticleSavedUseCase: IsArticleSavedUseCase
) : AndroidViewModel(Application()) {
    private val _state = MutableStateFlow(SavedState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSavedArticles()
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveArticleUseCase.invoke(article)
            getSavedArticles()
        }
    }

    private suspend fun getSavedArticles() {
        getSavedArticleUseCase.invoke(
            onSuccess = { savedArticles ->
                _state.update { it.copy(savedArticles = savedArticles) }
            }
        )
    }

    suspend fun isArticleSaved(article: Article): Boolean {
        return isArticleSavedUseCase.invoke(article)
    }
}
