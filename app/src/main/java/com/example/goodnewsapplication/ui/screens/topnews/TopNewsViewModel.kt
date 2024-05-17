package com.example.goodnewsapplication.ui.screens.topnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnewsapplication.domain.news.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TopNewsViewModel (
    private val newsRepository: NewsRepository,
): ViewModel() {

    //private val _topNewsUiState = MutableStateFlow(TopNewsUiState())

    val topNewsUiState: StateFlow<TopNewsUiState> =
        newsRepository.getTopNewsItems()
            .map { TopNewsUiState(newsList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TopNewsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}