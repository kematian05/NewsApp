package com.example.newsapp.data.domain

import android.content.Context
import androidx.room.Room
import com.example.newsapp.db.SavedArticleDao
import com.example.newsapp.db.SavedArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SavedArticleDatabase {
        return Room.databaseBuilder(
            context,
            SavedArticleDatabase::class.java,
            "saved_articles_db"
        ).build()
    }

    @Provides
    fun provideSavedArticleDao(database: SavedArticleDatabase): SavedArticleDao {
        return database.savedArticleDao()
    }
}