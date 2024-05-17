package com.example.goodnewsapplication.ui.screens.home

import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.ui.navigation.GoodNewsDestinations

data class HomeUiState(
    val newsList: List<NewsEntity> = emptyList(),
    val currentScreen: String = GoodNewsDestinations.HOME_ROUTE,
)
