package com.example.goodnewsapplication.data.weather

import com.example.goodnewsapplication.domain.weather.WeatherDataInfo
import com.example.goodnewsapplication.domain.weather.WeatherRepository

class WeatherRepositoryImpl (
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherDataInfo =
        weatherApiService.getWeatherData(latitude = latitude, longitude = longitude).toWeatherDataInfo()

}