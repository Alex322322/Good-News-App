package com.example.goodnewsapplication.ui.screens.likes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnewsapplication.domain.news.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LikedNewsViewModel (
    private val newsRepository: NewsRepository,
): ViewModel() {

    //private val _likedNewsUiState = MutableStateFlow(LikedNewsUiState())

    val likedNewsUiState: StateFlow<LikedNewsUiState> =
        newsRepository.getLikedNewsItems()
            .map { LikedNewsUiState(newsList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = LikedNewsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // Not implemented yet
    /*
    fun updateCurrentScreen(goodNewsScreensRoutes: String) {
        _likedNewsUiState.update {
            it.copy(
                currentScreen = goodNewsScreensRoutes
            )
        }
    }
     */
}