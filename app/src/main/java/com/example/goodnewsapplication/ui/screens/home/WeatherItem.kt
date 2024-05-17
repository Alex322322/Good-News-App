package com.example.goodnewsapplication.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.domain.weather.WeatherDataItem
import com.example.goodnewsapplication.domain.weather.WeatherType
import com.example.goodnewsapplication.ui.AppViewModelProvider
import com.example.goodnewsapplication.ui.screens.weather.WeatherUiState
import com.example.goodnewsapplication.ui.screens.weather.WeatherViewModel
import java.time.LocalDateTime

@Composable
fun WeatherItem(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: WeatherViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    when (viewModel.weatherUiState) {
        is WeatherUiState.Success -> WeatherCard(
            (viewModel.weatherUiState as WeatherUiState.Success).weatherData.weatherDataCurrent,
            paddingValues = paddingValues,
            modifier.padding(all = dimensionResource(id = R.dimen.padding_small))
        )
        is WeatherUiState.Loading -> LoadingWeatherItem(modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp))
        is WeatherUiState.Error -> ErrorWeatherItem(modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            retryAction = { viewModel.getWeatherInfo() }
        )
    }
}

@Composable
fun WeatherCard(
    weatherDataItem: WeatherDataItem,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(paddingValues),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = dimensionResource(id = R.dimen.padding_extra_small))
            ) {
                Text(
                    text = stringResource(R.string.today),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "${stringResource(R.string.temperature)} ${weatherDataItem.temperature}${stringResource(R.string.temperature_celsius)}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "${stringResource(R.string.humidity)} ${weatherDataItem.humidity}${stringResource(R.string.humidity_percent)}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "${stringResource(R.string.wind_speed)} ${weatherDataItem.windSpeed} ${stringResource(R.string.wind_speed_km)}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Image(
                modifier = modifier
                    .size(dimensionResource(R.dimen.weather_image_size))
                    .weight(1f)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = weatherDataItem.weatherCode.iconRes),
                contentDescription = weatherDataItem.weatherCode.weatherDesc
            )
        }
    }
}

@Composable
fun LoadingWeatherItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Default.Downloading,
                contentDescription = stringResource(R.string.loading),
                modifier = modifier.size(dimensionResource(R.dimen.loading_image_size))
            )
            Text(text = stringResource(id = R.string.loading_weather_data))
        }
    }
}

@Composable
fun ErrorWeatherItem(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Default.CloudOff,
                contentDescription = stringResource(R.string.error),
                modifier = modifier.size(dimensionResource(R.dimen.error_image_size))
            )
            Text(
                text = stringResource(R.string.loading_failed),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
            Button(onClick = retryAction) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun WeatherCardPreview() {
    WeatherCard(
        weatherDataItem = WeatherDataItem(
            time = LocalDateTime.now(),
            temperature = 25.6,
            humidity = 50,
            weatherCode = WeatherType.Foggy,
            windSpeed = 8.1
        ),
        paddingValues = PaddingValues(dimensionResource(id = R.dimen.padding_values_zero))
    )
}