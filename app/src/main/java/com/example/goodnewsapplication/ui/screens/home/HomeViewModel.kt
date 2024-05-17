package com.example.goodnewsapplication.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.domain.news.NewsRepository
import com.example.goodnewsapplication.ui.navigation.GoodNewsDestinations
import com.example.goodnewsapplication.ui.screens.newsitem.NewsItemUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel (
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository,
): ViewModel() {

    // private val _homeUiState = MutableStateFlow(HomeUiState())

    val homeUiState: StateFlow<HomeUiState> =
        newsRepository.getAllNewsItems()
            .map { HomeUiState(newsList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // Not implemented yet
    /*
    fun updateCurrentScreen(goodNewsScreensRoutes: String) {
        _homeUiState.update {
            it.copy(
                currentScreen = goodNewsScreensRoutes
            )
        }
    }
     */

    private val newsItemId: Int = savedStateHandle[GoodNewsDestinations.NEWS_ITEM_ID] ?: 0

    // News item details ui state
    // The data is retrieved from newsRepository and mapped to the UI state
    val newsItemUiState: StateFlow<NewsItemUiState> = newsRepository.getNewsItem(newsItemId)
        .filterNotNull()
        .map {
            NewsItemUiState(newsItemDetails = it.toNewsItemDetails())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = NewsItemUiState()
        )

    fun updateLikesNewsItem() {
        viewModelScope.launch {
            val currentItem = newsItemUiState.value.newsItemDetails.toNewsEntity()
            if (!currentItem.liked) {
                newsRepository.update(currentItem.copy(liked = true))
            } else {
                newsRepository.update(currentItem.copy(liked = false))
            }
        }
    }

    fun updateReadLaterNewsItem() {
        viewModelScope.launch {
            val currentItem = newsItemUiState.value.newsItemDetails.toNewsEntity()
            if (!currentItem.later) {
                newsRepository.update(currentItem.copy(later = true))
            } else {
                newsRepository.update(currentItem.copy(later = false))
            }
        }
    }
}

data class NewsItemDetails(
    val id: Int = 0,
    val label: String = "",
    val author: String = "",
    val text: String = "",
    val date: String = "",
    val rating: Double = 0.0,
    val picture: String = "",
    val liked: Boolean = false,
    val later: Boolean = false,
)

//Extension function to convert NewsEntity object  to NewsItemDetails
fun NewsEntity.toNewsItemDetails(): NewsItemDetails = NewsItemDetails(
    id = id,
    label = label,
    author = author,
    text = text,
    date = date,
    rating = rating,
    picture = picture,
    liked = liked,
    later = later,
)

//Extension function to convert NewsEntity object  to NewsItemDetails
fun NewsItemDetails.toNewsEntity(): NewsEntity = NewsEntity(
    id = id,
    label = label,
    author = author,
    text = text,
    date = date,
    rating = rating,
    picture = picture,
    liked = liked,
    later = later,
)