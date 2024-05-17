package com.example.goodnewsapplication.data.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val label: String,
    val author: String,
    val text: String,
    val date: String,
    val rating: Double,
    val picture: String,
    val liked: Boolean,
    val later: Boolean
)
