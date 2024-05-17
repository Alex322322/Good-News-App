package com.example.goodnewsapplication.ui.screens.readlater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnewsapplication.domain.news.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ReadLaterViewModel (
    private val newsRepository: NewsRepository,
): ViewModel() {

    //private val _readLaterUiState = MutableStateFlow(ReadLaterUiState())

    val readLaterUiState: StateFlow<ReadLaterUiState> =
        newsRepository.getLaterNewsItems()
            .map { ReadLaterUiState(newsList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ReadLaterUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // Not implemented yet
    /*
    fun updateCurrentScreen(goodNewsScreensRoutes: String) {
        _readLaterUiState.update {
            it.copy(
                currentScreen = goodNewsScreensRoutes
            )
        }
    }
     */
}