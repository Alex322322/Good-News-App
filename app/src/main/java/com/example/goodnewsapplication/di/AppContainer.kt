package com.example.goodnewsapplication.di

import android.app.Application
import android.content.Context
import com.example.goodnewsapplication.data.news.NewsDataBase
import com.example.goodnewsapplication.data.news.OfflineNewsRepository
import com.example.goodnewsapplication.data.weather.LocationTrackerImpl
import com.example.goodnewsapplication.data.weather.WeatherApiService
import com.example.goodnewsapplication.data.weather.WeatherRepositoryImpl
import com.example.goodnewsapplication.data.weather.provideFusedLocationProviderClient
import com.example.goodnewsapplication.domain.news.NewsRepository
import com.example.goodnewsapplication.domain.weather.LocationTracker
import com.example.goodnewsapplication.domain.weather.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val weatherRepository: WeatherRepository
    val newsRepository: NewsRepository
    val locationTracker: LocationTracker
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    // Weather
    private val interceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger.DEFAULT
    )
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    private val baseUrl = "https://api.open-meteo.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    override val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(retrofitService)
    }

    private val application : Application = context.applicationContext as Application
    override val locationTracker: LocationTracker by lazy {
        LocationTrackerImpl(provideFusedLocationProviderClient(application), application)
    }


    // News
    override val newsRepository: NewsRepository by lazy {
        OfflineNewsRepository(NewsDataBase.getDatabase(context).newsDao())
    }
}