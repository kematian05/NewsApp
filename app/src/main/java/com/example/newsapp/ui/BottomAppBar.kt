package com.example.newsapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.R

@Composable
fun BottomAppBar(navController: NavController) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        Canvas(
            modifier = Modifier.fillMaxWidth()
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = {
                if (navController.currentBackStackEntry?.destination?.route != "home") {
                    navController.popBackStack()
                    navController.navigate("home")
                }
            }) {
                Image(
                    painter = if (navController.currentBackStackEntry?.destination?.route == "home") {
                        painterResource(id = R.drawable.home_filled)
                    } else {
                        painterResource(id = R.drawable.home)
                    }, contentDescription = "News Catcher", modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = {
                if (navController.currentBackStackEntry?.destination?.route != "saved") {
                    navController.popBackStack()
                    navController.navigate("saved")
                }
            }) {
                Image(
                    painter = if (navController.currentBackStackEntry?.destination?.route == "saved") {
                        painterResource(id = R.drawable.saved_filled)
                    } else {
                        painterResource(id = R.drawable.saved)
                    }, contentDescription = "News Catcher", modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}