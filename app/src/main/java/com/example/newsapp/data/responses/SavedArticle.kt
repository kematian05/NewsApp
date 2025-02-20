package com.example.newsapp.data.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_articles")
data class SavedArticle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val savedAt: Long = System.currentTimeMillis()
)