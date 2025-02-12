package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.domain.SavedArticle

@Database(entities = [SavedArticle::class], version = 1, exportSchema = false)
abstract class SavedArticleDatabase : RoomDatabase() {
    abstract fun savedArticleDao(): SavedArticleDao

    companion object {
        @Volatile
        private var INSTANCE: SavedArticleDatabase? = null

        fun getDatabase(context: Context): SavedArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedArticleDatabase::class.java,
                    "saved_articles_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}