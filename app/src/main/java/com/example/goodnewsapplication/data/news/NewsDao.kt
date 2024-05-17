package com.example.goodnewsapplication.data.news

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsEntity: NewsEntity)

    @Update
    suspend fun update(newsEntity: NewsEntity)

    @Delete
    suspend fun delete(newsEntity: NewsEntity)

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsItem(id: Int): Flow<NewsEntity>

    @Query("SELECT * FROM news")
    fun getAllNewsItems(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news ORDER BY rating DESC LIMIT 5")
    fun getTopNewsItems(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE liked")
    fun getLikedNewsItems(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE later")
    fun getLaterNewsItems(): Flow<List<NewsEntity>>
}