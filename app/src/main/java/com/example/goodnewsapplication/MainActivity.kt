package com.example.goodnewsapplication

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.goodnewsapplication.ui.AppViewModelProvider
import com.example.goodnewsapplication.ui.screens.weather.WeatherViewModel
import com.example.goodnewsapplication.ui.theme.GoodNewsApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels(factoryProducer = { AppViewModelProvider.Factory })
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.getWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        enableEdgeToEdge()
        setContent {
            GoodNewsApplicationTheme {
                Surface {
                    val windowSize = calculateWindowSizeClass(this)
                    GoodNewsApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}
