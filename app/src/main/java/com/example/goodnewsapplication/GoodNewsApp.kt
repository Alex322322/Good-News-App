package com.example.goodnewsapplication

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goodnewsapplication.ui.navigation.GoodNewsNavGraph

@Composable
fun GoodNewsApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    GoodNewsNavGraph(
        windowSize = windowSize,
        navController = navController
    )
}