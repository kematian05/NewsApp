package com.example.newsapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.R
import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.Language
import com.example.newsapp.utils.disableSplitMotionEvents
import com.example.newsapp.utils.maxScrollFlingBehavior
import com.example.newsapp.viewModels.HomeState
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    state: HomeState,
    onGoToHome: () -> Unit,
    onGoToSaved: () -> Unit,
    onGoToDetails: (Article) -> Unit,
    onLanguageChange: (Language) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    currentRoute: String,
    context: Context
) {
    if (state.isLoading) {
        LoadingScreen(context = context)
    } else if (state.error.isNotEmpty()) {
        Text(text = state.error)
    } else {
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
                    verticalArrangement = Arrangement.Top,
                ) {
                    item {
                        LatestNewsFeed(
                            state = state,
                            onGoToDetails = onGoToDetails,
                            context = context
                        )
                    }
                    item {
                        Search(
                            state = state,
                            onSearchTextChange = onSearchTextChange,
                            onSearchSubmit = onSearchSubmit,
                            context = context
                        )
                    }
                    item {
                        DropDownMenu(
                            onLanguageChange = onLanguageChange,
                            context = context
                        )
                    }
                    items(state.filteredArticles) { article ->
                        if (state.isLoadingSearch) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Gray.copy(alpha = 0.3f))
                            )
                        }
                        NewsCard(
                            article = article,
                            onGoToDetails = onGoToDetails,
                            context = context
                        )
                    }
                }
            }
        }
    }
}


fun getDaySuffix(day: Int): String {
    return when {
        day in 11..13 -> "th"
        else -> when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LatestNewsCard(
    article: Article,
    onGoToDetails: (Article) -> Unit,
    context: Context
) {
//    val selectedLanguage = viewModel.selectedLanguage.collectAsState()
    Box(
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .height(180.dp)
            .width(320.dp)
            .clickable {
                Log.d("LatestNewsCard", "Clicked on ${article.url}")
                onGoToDetails(article)
            }
    ) {
        GlideImage(
            model = article.urlToImage,
            failure = placeholder(R.drawable.test),
            loading = placeholder(R.drawable.test),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .width(86.dp)
                .height(22.dp)
                .align(Alignment.TopStart)
                .background(
                    color = Color(0xFFD7D7D7).copy(alpha = 0.6f), shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
//                text = when (selectedLanguage.value) {
//                    Language.ENGLISH -> context.getString(R.string.news)
//                    Language.RUSSIAN -> context.getString(R.string.news)
//                },
                text = context.getString(R.string.news),
                style = TextStyle(color = Color.Black, fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),
            )
        }
        Row(
            modifier = Modifier
                .width(284.dp)
                .height(56.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = article.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter, FontWeight.Bold)),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentSize(align = Alignment.CenterStart),
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun LatestNewsFeed(state: HomeState, onGoToDetails: (Article) -> Unit, context: Context) {
    LazyRow(
        flingBehavior = maxScrollFlingBehavior(),
        modifier = Modifier
            .disableSplitMotionEvents()
    ) {
        items(state.headlines) { article ->
            LatestNewsCard(
                article = article,
                onGoToDetails = onGoToDetails,
                context = context
            )
        }
    }
}

@Composable
fun Search(
    state: HomeState,
    onSearchTextChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    context: Context
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(value = state.searchQuery, onValueChange = {
        onSearchTextChange(it)
    }, placeholder = { Text(text = context.getString(R.string.search)) }, textStyle = TextStyle(
        color = Color.Black, fontSize = 16.sp
    ), colors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        cursorColor = Color.Black
    ), maxLines = 1, modifier = Modifier
        .padding(16.dp)
//            .height(40.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .border(1.dp, Color(0xFFE2E2E2), RoundedCornerShape(20.dp)),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSearchSubmit(state.searchQuery)
                keyboardController?.hide()
            }
        )
    )

    LaunchedEffect(key1 = state.searchQuery) {
        delay(1000)
        onSearchSubmit(state.searchQuery)
    }
}

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    onLanguageChange: (Language) -> Unit,
    context: Context
) {
    val langChanged = Toast.makeText(
        context, "empty", Toast.LENGTH_SHORT
    )
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(26.dp)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxSize()
        ) {
            DropdownMenuItem(text = { Text("EN") }, onClick = {
                onLanguageChange(Language.ENGLISH)
                expanded = false
                langChanged.setText("Language changed to English")
                langChanged.show()
            })
            DropdownMenuItem(text = { Text("RU") }, onClick = {
                onLanguageChange(Language.RUSSIAN)
                expanded = false
                langChanged.setText("Язык изменен на русский")
                langChanged.show()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Testing() {
//    BottomAppBar(navController = NavController(LocalContext.current))
    DropDownMenu(onLanguageChange = {}, context = LocalContext.current)
//    HomeScreen(navController = NavController(LocalContext.current), viewModel = NewsViewModel())
}