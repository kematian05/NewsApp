package com.example.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.responses.SavedArticle

@Dao
interface SavedArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveArticle(savedArticle: SavedArticle)

    @Query("DELETE FROM saved_articles WHERE url = :articleUrl")
    suspend fun deleteArticle(articleUrl: String)

    @Query("SELECT * FROM saved_articles ORDER BY savedAt DESC")
    suspend fun getSavedArticles(): List<SavedArticle>

    @Query("SELECT COUNT(*) FROM saved_articles WHERE url = :articleUrl")
    suspend fun isArticleSaved(articleUrl: String): Int

}
