package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.R
import com.example.newsapp.data.responses.Article
import com.example.newsapp.viewModels.SavedNewsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    onGoToBack: () -> Unit,
    isSaved: Boolean,
    article: Article,
    onSaveClicked: (Article) -> Unit
) {
    MaterialTheme {
        Scaffold {
            NewsDetail(
                onGoToBack = onGoToBack,
                article = article,
                isSaved = isSaved,
                onSaveClicked = onSaveClicked
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("MissingColorAlphaChannel")
@Composable
fun NewsDetail(
    onGoToBack: () -> Unit,
    article: Article,
    isSaved: Boolean,
    onSaveClicked: (Article) -> Unit
) {
    val context = LocalContext.current
    Box(
//        modifier = Modifier.navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
                .height(250.dp)
        ) {
            GlideImage(
                model = article.urlToImage,
//                model = R.drawable.test,
                failure = placeholder(R.drawable.test),
                loading = placeholder(R.drawable.test),
                contentDescription = article.description,
//                contentDescription = "test",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                DropDownSaveShareMenu(article = article, isSaved = isSaved, onSaveClicked = onSaveClicked)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(
                        color = Color(0xFFD7D7D7).copy(alpha = 0.6f), shape = CircleShape
                    )
            ) {
                GoBackButton(onGoToBack = onGoToBack, article = article)
            }
        }
        Card(
            modifier = Modifier
                .padding(top = 250.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(86.dp)
                        .height(22.dp)
                        .background(
                            color = Color(0xFFD7D7D7).copy(alpha = 0.6f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = "Finance",
                        style = TextStyle(
                            color = Color.Black, fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center),
                    )
                }
                Text(
                    text = article.title,
//                    text = "Factbox: Who is still buying Russian crude oil?",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    ), modifier = Modifier
                        .padding(top = 16.dp)
                        .height(105.dp)
                )
                Text(text = article.author?.let {
                    val words = it.split(" ")
                    val result = if (words.size >= 2) words.take(2).joinToString(" ") else it
                    result.removeSuffix(",")
                } ?: "Vusat Orujov", style = TextStyle(
                    color = Color(0xFF0AA7FF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                ), modifier = Modifier
                    .padding(top = 8.dp)
                    .height(20.dp)
                    .align(Alignment.End))
                Text(
                    text = article.description,
//                    text = "Australia, Britain, Canada and the United States have imposed outright bans on Russian oil purchases following Moscow's invasion of Ukraine, but members of the European Union are split.",
                    maxLines = 5, style = TextStyle(
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    ), modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = article.content,
//                    text = "(Reuters) - Australia, Britain, Canada and the United States have imposed outright bans on Russian oil purchases following Moscow's invasion of Ukraine, but members of the European Union are split.EU foreign ministers failed to agree on Monday on sanctioning Russian gas and oil supplies, which account for 40% and 27% of the bloc's total use of those commodities respectively.Germany, the EU's top user of Russian crude oil and the Netherlands, a key trading hub, argue that the EU couldn't cut its dependence on Russian supplies overnight.",
                    style = TextStyle(
                        color = Color(0xFF89969C), fontSize = 12.sp, textAlign = TextAlign.Left
                    ), modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
                Text(text = "Read more...", style = TextStyle(
                    color = Color(0xFF57A5D1), fontSize = 12.sp, textAlign = TextAlign.Right
                ), modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                        context.startActivity(intent)
                    })
            }
        }
    }
}

@Composable
fun DropDownSaveShareMenu(
    modifier: Modifier = Modifier,
    article: Article,
    isSaved: Boolean,
    onSaveClicked: (Article) -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .wrapContentSize(Alignment.TopEnd)
            .background(
                color = Color(0xFFC4C4C4).copy(alpha = 0.48f), shape = CircleShape
            )
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = {
                        Row(
//                        modifier = Modifier.padding(16.dp)
                        ) {
                            IconButton(onClick = {
                                Log.d("DetailScreen", "Save clicked")
                                onSaveClicked(article)
                                expanded = false
                                if (isSaved) Toast.makeText(
                                    context, "Article unsaved", Toast.LENGTH_SHORT
                                ).show()
                                else Toast.makeText(context, "Article saved", Toast.LENGTH_SHORT)
                                    .show()
                            }) {
                                Image(
                                    painter = if (isSaved) painterResource(id = R.drawable.saved_filled)
                                    else painterResource(id = R.drawable.saved),
                                    contentDescription = "Save"
                                )
                            }
                            Text(
                                text = if (isSaved) "Saved" else "Save",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 32.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    },
                    onClick = {
                        Log.d("DetailScreen", "Save clicked")
                        onSaveClicked(article)
                        expanded = false
                        if (isSaved) Toast.makeText(context, "Article unsaved", Toast.LENGTH_SHORT)
                            .show()
                        else Toast.makeText(context, "Article saved", Toast.LENGTH_SHORT).show()
                    },
                )
                DropdownMenuItem(
                    text = {
                        Row(
//                        modifier = Modifier.padding(16.dp)
                        ) {
                            IconButton(onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "${article.title}\n${article.url}")
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent, "Share via"
                                    )
                                )
                                expanded = false
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.share),
                                    contentDescription = "Share"
                                )
                            }
                            Text(
                                text = "Share",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 32.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "${article.title}\n${article.url}")
                        }
                        context.startActivity(
                            Intent.createChooser(
                                shareIntent, "Share via"
                            )
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun GoBackButton(modifier: Modifier = Modifier, article: Article, onGoToBack: () -> Unit) {
    IconButton(
        onClick = {
            onGoToBack()
        }, modifier = Modifier
            .width(120.dp)
            .height(30.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = article.title.substring(0, 13) + "...",
                textAlign = TextAlign.Left,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter, FontWeight.Bold)),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x0000000)
@Composable
fun TestDetailScreen() {
//    GoBackButton(modifier = Modifier, navController = NavController(LocalContext.current))
//    DetailScreen(navController = NavController(LocalContext.current), viewModel = com.example.newsapp.viewModels.NewsViewModel())
}