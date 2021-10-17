package com.faraji.newsapp.db

import androidx.room.*
import com.faraji.newsapp.core.domain.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): MutableList<Article>

    @Delete
    suspend fun deleteArticle(article: Article)
}