package com.example.goodnewsapplication.data.news

import com.example.goodnewsapplication.domain.news.NewsRepository
import kotlinx.coroutines.flow.Flow

class OfflineNewsRepository(private val newsDao: NewsDao) : NewsRepository {
    override suspend fun insert(newsEntity: NewsEntity) = newsDao.insert(newsEntity)

    override suspend fun update(newsEntity: NewsEntity) = newsDao.update(newsEntity)

    override suspend fun delete(newsEntity: NewsEntity) = newsDao.delete(newsEntity)

    override fun getNewsItem(id: Int): Flow<NewsEntity> = newsDao.getNewsItem(id)

    override fun getAllNewsItems(): Flow<List<NewsEntity>> = newsDao.getAllNewsItems()

    override fun getTopNewsItems(): Flow<List<NewsEntity>> = newsDao.getTopNewsItems()

    override fun getLikedNewsItems(): Flow<List<NewsEntity>> = newsDao.getLikedNewsItems()

    override fun getLaterNewsItems(): Flow<List<NewsEntity>> = newsDao.getLaterNewsItems()
}