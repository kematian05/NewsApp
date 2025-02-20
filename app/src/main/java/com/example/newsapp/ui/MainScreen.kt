package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.api.NewsApi
import com.example.newsapp.data.domain.DatabaseModule
import com.example.newsapp.data.domain.GetArticlesUseCase
import com.example.newsapp.data.domain.GetSavedArticleUseCase
import com.example.newsapp.data.domain.IsArticleSavedUseCase
import com.example.newsapp.data.domain.NetworkModule
import com.example.newsapp.data.domain.SaveArticleUseCase
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.SavedArticlesRepository
import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.Language
import com.example.newsapp.db.SavedArticleDao
import com.example.newsapp.db.SavedArticleDatabase
import com.example.newsapp.viewModels.NewsViewModel
import com.example.newsapp.viewModels.SavedNewsViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.URLDecoder
import java.net.URLEncoder

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val savedNewsViewModel = SavedNewsViewModel(
        getSavedArticleUseCase = GetSavedArticleUseCase(
            SavedArticlesRepository(DatabaseModule().provideSavedArticleDao(DatabaseModule().provideDatabase(context)))
        ),
        saveArticleUseCase = SaveArticleUseCase(
            SavedArticlesRepository(DatabaseModule().provideSavedArticleDao(DatabaseModule().provideDatabase(context)))
        ),
        isArticleSavedUseCase = IsArticleSavedUseCase(
            SavedArticlesRepository(DatabaseModule().provideSavedArticleDao(DatabaseModule().provideDatabase(context)))
        )
    )

    val onGoToHome: () -> Unit = {
        if (navController.currentBackStackEntry?.destination?.route != "home") {
            navController.popBackStack()
            navController.navigate("home")
        }
    }

    val onGoToSaved: () -> Unit = {
        if (navController.currentBackStackEntry?.destination?.route != "saved") {
            navController.popBackStack()
            navController.navigate("saved")
        }
    }

    val onGoToDetails: (Article) -> Unit = { article ->
        val encodedJson = URLEncoder.encode(Gson().toJson(article), "UTF-8")
        navController.navigate("detail/$encodedJson")
    }

    val newsViewModel = NewsViewModel(
        getArticlesUseCase = GetArticlesUseCase(
            NewsRepository(newsApi = NetworkModule().provideNewsApi(NetworkModule().provideRetrofit()))
        ),
    )

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val onLanguageChange: (Language) -> Unit = { language ->
                newsViewModel.changeLanguage(language = language, context = context)
            }

            val onSearchTextChange: (String) -> Unit = { query ->
                newsViewModel.onSearchTextChanged(text = query)
            }

            val state by newsViewModel.state.collectAsStateWithLifecycle()

            HomeScreen(
                state = state,
                navController = navController,
                onGoToHome = onGoToHome,
                onGoToSaved = onGoToSaved,
                onGoToDetails = onGoToDetails,
                onLanguageChange = onLanguageChange,
                onSearchTextChange = onSearchTextChange,
                context = context
            )
        }
        composable(
            "detail/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->

            val onGoToBack: () -> Unit = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            }

            val articleJson = backStackEntry.arguments?.getString("articleJson")
            val decodedJson = URLDecoder.decode(articleJson, "UTF-8")
            val article = Gson().fromJson(decodedJson, Article::class.java)

            var isSaved by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = article) {
                isSaved = savedNewsViewModel.isArticleSaved(article)
            }

            val onSaveClicked: (Article) -> Unit = { article ->
                savedNewsViewModel.saveArticle(article)
                isSaved = !isSaved
            }

            DetailScreen(
                onGoToBack = onGoToBack,
                article = article,
                onSaveClicked = onSaveClicked,
                isSaved = isSaved,
            )
        }
        composable("saved") {
            val state by savedNewsViewModel.state.collectAsStateWithLifecycle()

            SavedScreen(
                navController = navController,
                onGoToHome = onGoToHome,
                onGoToSaved = onGoToSaved,
                onGoToDetails = onGoToDetails,
                state = state,
                context = context
            )
        }
    }
}