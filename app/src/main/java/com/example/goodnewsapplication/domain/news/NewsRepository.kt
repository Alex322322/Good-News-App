package com.example.goodnewsapplication.domain.news


import com.example.goodnewsapplication.data.news.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun insert(newsEntity: NewsEntity)

    suspend fun update(newsEntity: NewsEntity)

    suspend fun delete(newsEntity: NewsEntity)

    fun getNewsItem(id: Int): Flow<NewsEntity>

    fun getAllNewsItems(): Flow<List<NewsEntity>>

    fun getTopNewsItems(): Flow<List<NewsEntity>>

    fun getLikedNewsItems(): Flow<List<NewsEntity>>

    fun getLaterNewsItems(): Flow<List<NewsEntity>>
}