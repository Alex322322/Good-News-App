package com.example.goodnewsapplication.ui.screens.readlater

import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.ui.navigation.GoodNewsDestinations

data class ReadLaterUiState(
    val newsList: List<NewsEntity> = emptyList(),
    val currentScreen: String = GoodNewsDestinations.READLATER_ROUTE,
)
