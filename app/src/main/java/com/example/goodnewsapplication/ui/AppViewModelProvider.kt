package com.example.goodnewsapplication.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.goodnewsapplication.GoodNewsApplication
import com.example.goodnewsapplication.ui.screens.home.HomeViewModel
import com.example.goodnewsapplication.ui.screens.likes.LikedNewsViewModel
import com.example.goodnewsapplication.ui.screens.readlater.ReadLaterViewModel
import com.example.goodnewsapplication.ui.screens.topnews.TopNewsViewModel
import com.example.goodnewsapplication.ui.screens.weather.WeatherViewModel

// Provides Factory to create instance of ViewModel for the entire app
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                this.createSavedStateHandle(),
                goodNewsApplication().container.newsRepository,
            )
        }
        // Initializer for WeatherViewModel
        initializer {
            WeatherViewModel(
                goodNewsApplication().container.weatherRepository,
                goodNewsApplication().container.locationTracker,
            )
        }
        // Initializer for LikedNewsViewModel
        initializer {
            LikedNewsViewModel(
                goodNewsApplication().container.newsRepository,
            )
        }
        // Initializer for ReadLaterViewModel
        initializer {
            ReadLaterViewModel(
                goodNewsApplication().container.newsRepository,
            )
        }
        // Initializer for TopNewsViewModel
        initializer {
            TopNewsViewModel(
                goodNewsApplication().container.newsRepository,
            )
        }
    }
}


// Extension function to queries for Application object and returns an instance of GoodNewsApplication
fun CreationExtras.goodNewsApplication(): GoodNewsApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GoodNewsApplication)