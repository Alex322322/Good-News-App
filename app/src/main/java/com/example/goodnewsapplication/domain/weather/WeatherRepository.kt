package com.example.goodnewsapplication.domain.weather

interface WeatherRepository {
    suspend fun getWeatherData(latitude: Double, longitude: Double) : WeatherDataInfo
}