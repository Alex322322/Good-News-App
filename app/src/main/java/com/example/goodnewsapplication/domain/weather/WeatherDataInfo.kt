package com.example.goodnewsapplication.domain.weather

data class WeatherDataInfo(
    val weatherDataHourly: List<WeatherDataItem>,
    val weatherDataCurrent: WeatherDataItem,
)
