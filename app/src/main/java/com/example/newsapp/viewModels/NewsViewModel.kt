package com.example.newsapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.LocaleHelper
import com.example.newsapp.data.domain.GetArticlesUseCase
import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.Language
import dagger.hilt.android.lifecycle.HiltViewModel
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
        searchArticles(text)
    }

    private fun loadHeadlines() {
        _state.update { old ->
            old.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            try {
                _state.update { old ->
                    old.copy(
                        headlines = getArticlesUseCase.invoke("Biden")
                    )
                }
            } catch (e: Exception) {
                _state.update { old ->
                    old.copy(
                        error = "Error fetching headlines"
                    )
                }
            } finally {
                _state.update { old ->
                    old.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadArticles(query: String) {
        _state.update { old ->
            old.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val initialArticles = getArticlesUseCase.invoke(query)
                _state.update { old ->
                    old.copy(
                        initialArticles = initialArticles,
                        filteredArticles = initialArticles
                    )
                }
            } catch (e: Exception) {
                _state.update { old ->
                    old.copy(
                        error = "Error fetching articles"
                    )
                }
            } finally {
                _state.update { old ->
                    old.copy(
                        isLoading = false
                    )
                }
            }
        }
    }


    fun changeLanguage(language: Language, context: Context) {
        LocaleHelper.setLocale(context, language.code)
        // TODO
    }

    private fun searchArticles(query: String) {
        if (query.isEmpty()) {
            _state.update { old ->
                old.copy(
                    filteredArticles = old.initialArticles
                )
            }
            return
        }
        _state.update { old ->
            old.copy(
                isLoadingSearch = true
            )
        }
        viewModelScope.launch {
            try {
                _state.update { old ->
                    old.copy(
                        filteredArticles = getArticlesUseCase.invoke(query)
                    )
                }
            } catch (e: Exception) {
                _state.update { old ->
                    old.copy(
                        error = "Error searching headlines"
                    )
                }
            } finally {
                _state.update { old ->
                    old.copy(
                        isLoadingSearch = false
                    )
                }
            }
        }
    }
}