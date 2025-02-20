package com.example.newsapp.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.R
import com.example.newsapp.data.responses.Article
import java.net.URLEncoder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(article: Article, onGoToDetails: (Article) -> Unit, context: Context) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                onGoToDetails(article)
            },
    ) {
        Log.d("NewsCard", "Image URL: ${article.urlToImage}")
        GlideImage(
            model = article.urlToImage,
            failure = placeholder(R.drawable.test),
            loading = placeholder(R.drawable.test),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(103.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier
//                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            Box(
                modifier = Modifier
                    .width(76.dp)
                    .height(17.dp)
                    .background(
                        color = Color(0xFFD7D7D7).copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text(
                    text = context.getString(R.string.finance),
                    style = TextStyle(color = Color.Black, fontSize = 12.sp),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                )
            }
            Text(
                text = article.title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter, FontWeight.Bold)),
                    fontWeight = FontWeight.Bold
                ),
                minLines = 2,
                maxLines = 2,
                modifier = Modifier
                    .width(245.dp)
                    .padding(top = 4.dp),
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier
                    .width(220.dp)
                    .height(17.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.publishedAt.substring(0, 10),
                    style = TextStyle(
                        color = Color(0xFF89969C), fontSize = 12.sp
                    ), modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = article.author?.let {
                        val words = it.split(" ")
                        val result = if (words.size >= 2) words.take(2).joinToString(" ") else it
                        result.removeSuffix(",")
                    } ?: "Vusat Orujov",
                    style = TextStyle(
                        color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold
                    ), modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}