package com.example.goodnewsapplication.ui.screens.likes

import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.ui.navigation.GoodNewsDestinations

data class LikedNewsUiState(
    val newsList: List<NewsEntity> = emptyList(),
    val currentScreen: String = GoodNewsDestinations.LIKES_ROUTE,
)
