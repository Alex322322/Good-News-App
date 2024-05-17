package com.example.goodnewsapplication.ui.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnewsapplication.domain.weather.LocationTracker
import com.example.goodnewsapplication.domain.weather.WeatherDataItem
import com.example.goodnewsapplication.domain.weather.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.Double.Companion.MIN_VALUE

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    init {
        getWeatherInfo()
    }

    fun getWeatherInfo() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                weatherUiState = try {
                    WeatherUiState.Success(
                        weatherRepository.getWeatherData(location.latitude, location.longitude)
                    )
                } catch (e: IOException) {
                    WeatherUiState.Error
                } catch (e: HttpException) {
                    WeatherUiState.Error
                }
            }
        }
    }

    fun getWeeklyWeatherForecast(weatherHourlyListDataItem: List<WeatherDataItem>): List<WeatherDataItem> {
        var maxTemperature: Double = MIN_VALUE
        var indexTemp = -1
        val result = mutableListOf<WeatherDataItem>()
        for (i in 0..167) {
            if (weatherHourlyListDataItem[i].temperature > maxTemperature) {
                maxTemperature = weatherHourlyListDataItem[i].temperature
                indexTemp = i
            }
            if ((i + 1) % 24 == 0) {
                result.add(weatherHourlyListDataItem[indexTemp])
                maxTemperature  = MIN_VALUE
                indexTemp = -1
            }
        }
        return result
    }

    fun getCurrentDayWeatherForecast(weatherCurrentDataItem: WeatherDataItem, weatherHourlyListDataItem: List<WeatherDataItem>): List<WeatherDataItem> {
        var indexTemp = -1
        val timeTemp = weatherCurrentDataItem.time.withMinute(0)
        for (i in 0..167) {
            if (weatherHourlyListDataItem[i].time == timeTemp) indexTemp = i
        }
        val result = mutableListOf<WeatherDataItem>()
        for (i in indexTemp..indexTemp + 24) {
            result.add(weatherHourlyListDataItem[i])
        }
        return result
    }
}