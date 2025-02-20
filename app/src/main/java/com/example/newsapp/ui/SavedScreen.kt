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
import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.SavedArticle
import com.example.newsapp.utils.disableSplitMotionEvents
import com.example.newsapp.utils.toArticle
import com.example.newsapp.viewModels.NewsViewModel
import com.example.newsapp.viewModels.SavedNewsViewModel
import com.example.newsapp.viewModels.SavedState

@Composable
fun SavedScreen(
    onGoToHome: () -> Unit,
    onGoToSaved: () -> Unit,
    onGoToDetails: (Article) -> Unit,
    state: SavedState,
    currentRoute: String,
    context: Context
) {
    Log.d("SavedScreen", "Saved articles: ${state.savedArticles.size}")
    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    onGoToHome = onGoToHome,
                    onGoToSaved = onGoToSaved,
                    currentRoute = currentRoute
                )
            },
            topBar = {
                TopAppBar()
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .disableSplitMotionEvents(),
                verticalArrangement = Arrangement.Top
            ) {
                items(state.savedArticles.size) { index ->
                    val article = state.savedArticles[index]
                    NewsCard(
                        article = article.toArticle(),
                        onGoToDetails = onGoToDetails,
                        context = context
                    )
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