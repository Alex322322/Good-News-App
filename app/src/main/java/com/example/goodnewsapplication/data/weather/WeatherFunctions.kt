package com.example.goodnewsapplication.data.weather

import com.example.goodnewsapplication.domain.weather.WeatherDataInfo
import com.example.goodnewsapplication.domain.weather.WeatherDataItem
import com.example.goodnewsapplication.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherCurrentInfo.toWeatherDataItem(): WeatherDataItem {
    return WeatherDataItem(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
        temperature = temperature,
        humidity = humidity,
        weatherCode = WeatherType.fromWMO(weatherCode),
        windSpeed = windSpeed,
    )
}

fun WeatherDataHourlyInfo.toWeatherDataItemList(): List<WeatherDataItem> {
    val resultList = mutableListOf<WeatherDataItem>()
    for(i in 0..time.size - 1) {
        resultList.add(
            WeatherDataItem(
                time = LocalDateTime.parse(time[i], DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperatures[i],
                humidity = humidities[i],
                weatherCode = WeatherType.fromWMO(weatherCodes[i]),
                windSpeed = windSpeeds[i],)
        )
    }
    return resultList
}

fun WeatherData.toWeatherDataInfo(): WeatherDataInfo {
    val weatherDataHourly = weatherDataHourly.toWeatherDataItemList()
    val weatherDataCurrent = weatherDataCurrent.toWeatherDataItem()
    return WeatherDataInfo(
        weatherDataHourly = weatherDataHourly,
        weatherDataCurrent = weatherDataCurrent
    )
}