package com.example.newsapp.data.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.api.ApiClient
import com.example.newsapp.data.domain.Article
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

class NewsRepository @Inject constructor() {

}