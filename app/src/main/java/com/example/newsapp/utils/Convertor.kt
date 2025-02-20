package com.example.newsapp.utils

import com.example.newsapp.data.responses.Article
import com.example.newsapp.data.responses.Source
import com.example.newsapp.data.responses.SavedArticle

fun SavedArticle.toArticle(): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        url = this.url,
        source = Source("", ""),
        urlToImage = this.urlToImage
    )
}

fun Article.toSavedArticle(): SavedArticle {
    return SavedArticle(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage,
        savedAt = System.currentTimeMillis()
    )
}