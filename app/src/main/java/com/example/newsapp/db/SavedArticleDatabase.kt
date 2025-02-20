package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.responses.SavedArticle

@Database(entities = [SavedArticle::class], version = 1, exportSchema = false)
abstract class SavedArticleDatabase : RoomDatabase() {
    abstract fun savedArticleDao(): SavedArticleDao
}