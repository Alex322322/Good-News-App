package com.example.goodnewsapplication.data.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    @SerialName(value = "hourly") val weatherDataHourly: WeatherDataHourlyInfo,
    @SerialName(value = "current") val weatherDataCurrent: WeatherCurrentInfo,
)

@Serializable
data class WeatherDataHourlyInfo(
    val time: List<String>,
    @SerialName(value = "temperature_2m") val temperatures: List<Double>,
    @SerialName(value = "relative_humidity_2m") val humidities: List<Int>,
    @SerialName(value = "weather_code") val weatherCodes: List<Int>,
    @SerialName(value = "wind_speed_10m") val windSpeeds: List<Double>,
)

@Serializable
data class WeatherCurrentInfo(
    val time: String = "",
    @SerialName(value = "temperature_2m") val temperature: Double = 0.0,
    @SerialName(value = "relative_humidity_2m") val humidity: Int = 0,
    @SerialName(value = "weather_code") val weatherCode: Int = 0,
    @SerialName(value = "wind_speed_10m") val windSpeed: Double = 0.0,
)