package com.example.newsapp.viewModels

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.LaunchedEffect
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.domain.GetArticlesUseCase
import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val getArticlesUseCase: GetArticlesUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadHeadlines()
        loadArticles("Trump")
    }


    fun onSearchTextChanged(text: String) {
        _state.update { old ->
            old.copy(
                searchQuery = text
            )
        }
    }

    fun onSearchSubmit(text: String) {
        searchArticles(text)
    }

    private fun loadHeadlines() {
        viewModelScope.launch {
            getArticlesUseCase.invoke(
                query = "Biden",
                onLoading = {
                    _state.update { old ->
                        old.copy(
                            isLoading = true
                        )
                    }
                },
                onSuccess = {
                    _state.update { old ->
                        old.copy(
                            headlines = it
                        )
                    }
                },
                onError = {
                    _state.update { old ->
                        old.copy(
                            error = it
                        )
                    }
                },
                onFinally = {
                    _state.update { old ->
                        old.copy(
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    private fun loadArticles(query: String) {
        viewModelScope.launch {
            getArticlesUseCase.invoke(
                query = query,
                onLoading = {
                    _state.update { old ->
                        old.copy(
                            isLoading = true
                        )
                    }
                },
                onSuccess = {
                    _state.update { old ->
                        old.copy(
                            initialArticles = it,
                            filteredArticles = it
                        )
                    }
                },
                onError = {
                    _state.update { old ->
                        old.copy(
                            error = it
                        )
                    }
                },
                onFinally = {
                    _state.update { old ->
                        old.copy(
                            isLoading = false
                        )
                    }
                }
            )
        }
    }


    fun changeLanguage(language: Language) {
        val appLocale = LocaleListCompat.forLanguageTags(language.code)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }


    private fun searchArticles(query: String) {
        Log.e("NewsViewModel", "Searching articles for query: $query")
        if (query.isEmpty()) {
            _state.update { old ->
                old.copy(
                    filteredArticles = old.initialArticles
                )
            }
            return
        }
        viewModelScope.launch {
            getArticlesUseCase.invoke(
                query = query,
                onLoading = {
                    _state.update { old ->
                        old.copy(
                            isLoadingSearch = true
                        )
                    }
                },
                onSuccess = {
                    _state.update { old ->
                        old.copy(
                            filteredArticles = it
                        )
                    }
                },
                onError = {
                    _state.update { old ->
                        old.copy(
                            error = it
                        )
                    }
                },
                onFinally = {
                    _state.update { old ->
                        old.copy(
                            isLoadingSearch = false
                        )
                    }
                }
            )
        }
    }
}