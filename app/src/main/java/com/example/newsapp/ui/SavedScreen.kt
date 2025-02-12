package com.example.newsapp.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.newsapp.data.domain.SavedArticle
import com.example.newsapp.utils.disableSplitMotionEvents
import com.example.newsapp.utils.toArticle
import com.example.newsapp.viewModels.NewsViewModel
import com.example.newsapp.viewModels.SavedNewsViewModel

@Composable
fun SavedScreen(navController: NavController, viewModel: SavedNewsViewModel, viewModel2: NewsViewModel, context: Context) {
    val savedArticles = remember { mutableStateOf<List<SavedArticle>>(emptyList()) }
    LaunchedEffect(Unit) {
        savedArticles.value = viewModel.getSavedArticles()
    }
    Log.d("SavedScreen", "Saved articles: ${savedArticles.value.size}")
    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(navController = navController)
            },
            topBar = {
                TopAppBar(viewModel = viewModel2)
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .disableSplitMotionEvents(),
                verticalArrangement = Arrangement.Top
            ) {
                items(savedArticles.value.size) { index ->
                    val article = savedArticles.value[index]
                    NewsCard(article = article.toArticle(), navController = navController, context = context)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Test() {
//    SavedScreen(navController = NavController(LocalContext.current))
}