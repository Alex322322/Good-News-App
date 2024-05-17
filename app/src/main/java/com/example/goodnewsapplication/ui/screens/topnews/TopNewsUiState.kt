package com.example.goodnewsapplication.ui.screens.topnews

import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.ui.navigation.GoodNewsDestinations

data class TopNewsUiState(
    val newsList: List<NewsEntity> = emptyList(),
    val currentScreen: String = GoodNewsDestinations.TOPNEWS_ROUTE,
)
