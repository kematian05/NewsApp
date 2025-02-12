package com.example.newsapp.viewModels

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.ApiClient
import com.example.newsapp.data.LocaleHelper
import com.example.newsapp.data.domain.Article
import com.example.newsapp.data.domain.Language
import com.example.newsapp.data.domain.NewsState
import com.example.newsapp.ui.NewsIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.awaitResponse
import java.util.Locale

class NewsViewModel : ViewModel() {
    private val _searchText = mutableStateOf("")
    private val _state = MutableStateFlow(NewsState())
    private val _selectedLanguage = MutableStateFlow(Language.ENGLISH)
    val searchText: State<String> = _searchText
    val state = _state.asStateFlow()
    val selectedLanguage = _selectedLanguage.asStateFlow()


    init {
        handleIntent(NewsIntent.LoadHeadlines)
        handleIntent(NewsIntent.LoadArticles("Trump"))
    }


    private fun handleIntent(intent: NewsIntent) {
        viewModelScope.launch {
            when (intent) {
                is NewsIntent.LoadHeadlines -> loadHeadlines()
                is NewsIntent.LoadArticles -> loadArticles(intent.query)
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        searchArticles(text)
    }

    private fun loadHeadlines() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val response =
                    ApiClient.newsApi.getTopHeadlines("Biden", "d44dd5bfd36449709d8ea5929718ad83")
                        .awaitResponse()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        headlines = response.body()?.articles ?: emptyList(),
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        error = "Error fetching headlines",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Exception fetching headlines",
                    isLoading = false
                )
            }
        }
    }

    private fun loadArticles(query: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val response =
                    ApiClient.newsApi.getTopHeadlines(query, "d44dd5bfd36449709d8ea5929718ad83")
                        .awaitResponse()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        articles = response.body()?.articles ?: emptyList(),
                        filteredArticles = response.body()?.articles ?: emptyList(),
                        isLoading = false
                    )
                    _state.value = _state.value.copy(
                        articles = _state.value.articles.sortedByDescending { it.publishedAt },
                        filteredArticles = _state.value.filteredArticles.sortedByDescending { it.publishedAt }
                    )
                } else {
                    _state.value = _state.value.copy(
                        error = "Error fetching articles",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Exception fetching articles",
                    isLoading = false
                )
            }
        }
    }

    fun getArticleByUrl(url: String?): Article? {
        return state.value.articles.find { it.url == url } ?: state.value.headlines.find { it.url == url }
    }

    fun changeLanguage(language: Language, context: Context) {
        _selectedLanguage.value = language
        when (language) {
            Language.ENGLISH -> LocaleHelper.setLocale(context, "en")
            Language.RUSSIAN -> LocaleHelper.setLocale(context, "ru")
        }
    }

    fun searchArticles(query: String) {
        _state.value = state.value.copy(
            filteredArticles = if (query.isEmpty()) state.value.articles
            else state.value.articles.filter { article ->
                article.title.contains(query, ignoreCase = true) ||
                        article.description.contains(query, ignoreCase = true)
            }
        )
    }

}