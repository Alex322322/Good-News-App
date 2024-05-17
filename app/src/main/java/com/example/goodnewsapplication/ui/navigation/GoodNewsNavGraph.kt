package com.example.goodnewsapplication.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.goodnewsapplication.ui.screens.home.HomeScreen
import com.example.goodnewsapplication.ui.screens.likes.LikedNewsScreen
import com.example.goodnewsapplication.ui.screens.newsitem.NewsItemScreen
import com.example.goodnewsapplication.ui.screens.readlater.ReadLaterScreen
import com.example.goodnewsapplication.ui.screens.topnews.TopNewsScreen
import com.example.goodnewsapplication.ui.screens.weather.WeatherForecastScreen

@Composable
fun GoodNewsNavGraph(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val goodNewsNavigationType: GoodNewsNavigationType
    val goodNewsContentType: GoodNewsContentType
    when(windowSize) {
        WindowWidthSizeClass.Compact -> {
            goodNewsNavigationType = GoodNewsNavigationType.BOTTOM_NAVIGATION
            goodNewsContentType = GoodNewsContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            goodNewsNavigationType = GoodNewsNavigationType.NAVIGATION_RAIL
            goodNewsContentType = GoodNewsContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            goodNewsNavigationType = GoodNewsNavigationType.NAVIGATION_RAIL
            goodNewsContentType = GoodNewsContentType.LIST_AND_DETAILS
        }
        else -> {
            goodNewsNavigationType = GoodNewsNavigationType.BOTTOM_NAVIGATION
            goodNewsContentType = GoodNewsContentType.LIST_ONLY
        }
    }
    NavHost(
        navController = navController,
        startDestination = GoodNewsDestinations.HOME_ROUTE,
        modifier = modifier
    ) {
        composable(route = GoodNewsDestinations.HOME_ROUTE) {
            HomeScreen(
                navController = navController,
                goodNewsNavigationType = goodNewsNavigationType,
                goodNewsContentType = goodNewsContentType,
                navigateToNewsItem = { 
                    navController.navigate("${GoodNewsDestinations.NEWSITEM_ROUTE}/${it}")//"${GoodNewsDestinations.NEWSITEM_ROUTE}/${it}")
                },
                navigateToWeatherScreen = {
                    navController.navigate(GoodNewsDestinations.WEATHER_ROUTE)
                }
            )
        }
        composable(route = GoodNewsDestinations.TOPNEWS_ROUTE) {
            TopNewsScreen(
                navController = navController,
                goodNewsNavigationType = goodNewsNavigationType,
                goodNewsContentType = goodNewsContentType,
                navigateToNewsItem = {
                    navController.navigate("${GoodNewsDestinations.NEWSITEM_ROUTE}/${it}")
                },)
        }
        composable(route = GoodNewsDestinations.LIKES_ROUTE) {
            LikedNewsScreen(
                navController = navController,
                goodNewsNavigationType = goodNewsNavigationType,
                goodNewsContentType = goodNewsContentType,
                navigateToNewsItem = {
                    navController.navigate("${GoodNewsDestinations.NEWSITEM_ROUTE}/${it}")
                },
                navigateToHomeScreen = {
                    navController.navigate(GoodNewsDestinations.HOME_ROUTE)
                })
        }
        composable(route = GoodNewsDestinations.READLATER_ROUTE) {
            ReadLaterScreen(
                navController = navController,
                goodNewsNavigationType = goodNewsNavigationType,
                goodNewsContentType = goodNewsContentType,
                navigateToNewsItem = {
                    navController.navigate("${GoodNewsDestinations.NEWSITEM_ROUTE}/${it}")
                },
                navigateToHomeScreen = {
                    navController.navigate(GoodNewsDestinations.HOME_ROUTE)
                }
            )
        }
        composable(route = GoodNewsDestinations.WEATHER_ROUTE) {
            WeatherForecastScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable(
            route = GoodNewsDestinations.NEWS_ROUTE_WITH_ID,
            arguments = listOf(navArgument(GoodNewsDestinations.NEWS_ITEM_ID) {
                type = NavType.IntType
            })
            ) {
            NewsItemScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}