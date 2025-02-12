package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.data.domain.Article
import com.example.newsapp.viewModels.NewsViewModel
import com.example.newsapp.viewModels.SavedNewsViewModel
import com.google.gson.Gson
import java.net.URLDecoder

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val newsViewModel = NewsViewModel()
    val context = LocalContext.current.applicationContext
    val savedNewsViewModel = SavedNewsViewModel(context as Application)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, newsViewModel, context)
        }
        composable(
            "detail/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")
            val decodedUrl = URLDecoder.decode(url, "UTF-8")
            val article = newsViewModel.getArticleByUrl(decodedUrl!!)
            DetailScreen(
                navController = navController,
                article = article!!,
                viewModel = savedNewsViewModel
            )
        }
        composable("saved") {
            SavedScreen(
                navController = navController,
                viewModel = savedNewsViewModel,
                viewModel2 = newsViewModel,
                context = context
            )
        }
    }
}