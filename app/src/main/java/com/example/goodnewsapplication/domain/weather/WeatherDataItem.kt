package com.example.goodnewsapplication.domain.weather

import java.time.LocalDateTime

data class WeatherDataItem(
    val time: LocalDateTime,
    val temperature: Double,
    val humidity: Int,
    val weatherCode: WeatherType,
    val windSpeed: Double,
)
