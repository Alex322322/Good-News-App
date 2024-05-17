package com.example.goodnewsapplication.ui.screens.weather

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherForecastScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: WeatherViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    when (viewModel.weatherUiState) {
        is WeatherUiState.Success -> WeatherForecastScreenBody(
            weatherCurrentDataItem = (viewModel.weatherUiState as WeatherUiState.Success).weatherData.weatherDataCurrent,
            weatherHourlyListDataItem = (viewModel.weatherUiState as WeatherUiState.Success).weatherData.weatherDataHourly,
            weeklyWeatherForecast = viewModel.getWeeklyWeatherForecast((viewModel.weatherUiState as WeatherUiState.Success).weatherData.weatherDataHourly),
            currentDayWeatherForecast = viewModel.getCurrentDayWeatherForecast(
                (viewModel.weatherUiState as WeatherUiState.Success).weatherData.weatherDataCurrent,
                (viewModel.weatherUiState as WeatherUiState.Success).weatherData.weatherDataHourly
            ),
            onBackPressed = onBackPressed,
            paddingValues = paddingValues,
            modifier = modifier.padding(all = 8.dp)
        )
        is WeatherUiState.Loading -> LoadingWeatherScreen(modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp))
        is WeatherUiState.Error -> ErrorWeatherScreen(modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            retryAction = { viewModel.getWeatherInfo() }
        )
    }
}

@Composable
fun WeatherForecastScreenBody(
    weatherCurrentDataItem: WeatherDataItem,
    weatherHourlyListDataItem: List<WeatherDataItem>,
    weeklyWeatherForecast: List<WeatherDataItem>,
    currentDayWeatherForecast: List<WeatherDataItem>,
    onBackPressed: () -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    Scaffold(
        topBar = {
            WeatherScreenTopAppBar(onBackButtonClicked = onBackPressed)
        }
    ) {
        padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            WeatherScreenTopItem(
                weatherDataItem = weatherCurrentDataItem,
                paddingValues = paddingValues
            )
            Text(
                text = stringResource(id = R.string.today_weather),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small),
                        start = dimensionResource(id = R.dimen.padding_medium),
                    )
            )
            WeatherScreenForecastDayItem(
                weatherHourlyListDataItem = currentDayWeatherForecast,
                paddingValues = paddingValues
            )
            Text(
                text = stringResource(id = R.string.weekly_forecast),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_medium),
                    )
            )
            WeeklyForecastItem(
                weatherHourlyListDataItem = weeklyWeatherForecast,
                paddingValues = paddingValues
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        }
    }
}

@Composable
fun WeatherScreenTopItem(
    weatherDataItem: WeatherDataItem,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_extra_small))
            .padding(paddingValues),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(R.string.current_weather),
                style = MaterialTheme.typography.headlineSmall,
            )
            Image(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = weatherDataItem.weatherCode.iconRes),
                contentDescription = weatherDataItem.weatherCode.weatherDesc
            )
            Text(
                text = "${weatherDataItem.temperature} ${stringResource(R.string.temperature_celsius)}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_small))
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = weatherDataItem.weatherCode.weatherDesc,
                style = MaterialTheme.typography.headlineMedium,
                modifier = modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                    .align(Alignment.CenterHorizontally)
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
    }
}

@Composable
fun WeeklyForecastItem(
    weatherHourlyListDataItem: List<WeatherDataItem>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyRow (modifier = modifier) {
        items(items = weatherHourlyListDataItem, key = {it.time}) { weatherDataItem ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherDataItem.time.format(
                        DateTimeFormatter.ofPattern("dd.MM")
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
                WeatherForecastRowItem(
                    weatherDataItem = weatherDataItem,
                    paddingValues = paddingValues,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_extra_small))
                )
            }
        }
    }
}

@Composable
fun WeatherScreenForecastDayItem(
    weatherHourlyListDataItem: List<WeatherDataItem>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(items = weatherHourlyListDataItem, key = {it.time}) { weatherDataItem ->
            WeatherRowItem(
                weatherDataItem = weatherDataItem,
                paddingValues = paddingValues,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_extra_small))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreenTopAppBar(
    onBackButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(),
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClicked,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.navigation_back)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.weather),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
    )
}

@Composable
fun WeatherRowItem(
    weatherDataItem: WeatherDataItem,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(paddingValues),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = weatherDataItem.time.format(
                    DateTimeFormatter.ofPattern("HH:mm")
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
            )
            Image(
                modifier = modifier
                    .size(dimensionResource(R.dimen.weather_forecast_image_size))
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = weatherDataItem.weatherCode.iconRes),
                contentDescription = weatherDataItem.weatherCode.weatherDesc
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small),
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = stringResource(id = R.string.thermostat_icon)
                )
                Text(
                    text = "${weatherDataItem.temperature}${stringResource(R.string.temperature_celsius)}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun WeatherForecastRowItem(
    weatherDataItem: WeatherDataItem,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(paddingValues),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier
                    .size(dimensionResource(R.dimen.weather_forecast_image_size))
                    .padding(top = dimensionResource(R.dimen.padding_small))
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = weatherDataItem.weatherCode.iconRes),
                contentDescription = weatherDataItem.weatherCode.weatherDesc
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = stringResource(id = R.string.thermostat_icon)
                )
                Text(
                    text = "${weatherDataItem.temperature}${stringResource(R.string.temperature_celsius)}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small)
                        )
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = stringResource(id = R.string.humidity_icon)
                )
                Text(
                    text = "${weatherDataItem.humidity}${stringResource(R.string.humidity_percent)}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small),
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.WindPower,
                    contentDescription = stringResource(id = R.string.wind_icon)
                )
                Text(
                    text = "${weatherDataItem.windSpeed} ${stringResource(R.string.wind_speed_km)}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun LoadingWeatherScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { WeatherScreenTopAppBar{} }
    ) { padding ->
        Card(
            modifier = modifier.padding(padding),
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

}

@Composable
fun ErrorWeatherScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { WeatherScreenTopAppBar{} }
    ) { padding ->
        Card(
            modifier = modifier.padding(padding),
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

}

@Preview(showSystemUi = true)
@Composable
private fun WeatherRowItemPreview() {
    WeatherRowItem(
        WeatherDataItem(LocalDateTime.parse("2024-05-14T00:00", DateTimeFormatter.ISO_DATE_TIME), humidity = 75, weatherCode = WeatherType.Foggy, windSpeed = 12.5, temperature = 20.7 ),
        paddingValues  = PaddingValues(0.dp),
        modifier = Modifier
    )
}