package com.example.goodnewsapplication.ui.screens.weather

import com.example.goodnewsapplication.domain.weather.WeatherDataInfo

sealed interface WeatherUiState {
    data class Success(val weatherData: WeatherDataInfo) : WeatherUiState
    data object Error : WeatherUiState
    data object Loading : WeatherUiState
}

