package com.example.newsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.viewModels.NewsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TopAppBar() {
    Column(
        modifier = Modifier
//            .systemBarsPadding()
            .windowInsetsPadding(insets = WindowInsets.statusBars)
//            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
//                .systemBarsPadding()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "News Catcher",
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "News Catcher", style = TextStyle(
                    fontSize = 22.sp, color = if (isSystemInDarkTheme()) Color.White else Color.Black, fontWeight = FontWeight.Bold
                ), modifier = Modifier
//                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        val date = LocalDate.now()
        val year = date.year
        val month =
            DateTimeFormatter.ofPattern("MMMM").format(date).replaceFirstChar(Char::uppercase)
        val day = date.dayOfMonth


        val dayWithSuffix = "$day${getDaySuffix(day)}"
        Text(
            text = "$month $dayWithSuffix, $year", style = TextStyle(
                fontSize = 14.sp, color = Color(0xFF89969C)
            ), modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 4.dp)
                .padding(bottom = 8.dp)
        )
    }
}