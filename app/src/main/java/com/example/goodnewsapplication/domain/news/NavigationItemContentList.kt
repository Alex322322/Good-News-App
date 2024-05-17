package com.example.goodnewsapplication.domain.news

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.ui.navigation.GoodNewsDestinations

val navigationItemContentList = listOf(
    NavigationItemContent(
        goodNewsScreensRoutes = GoodNewsDestinations.HOME_ROUTE,
        icon = Icons.Default.Person,
        text = R.string.for_you
    ),
    NavigationItemContent(
        goodNewsScreensRoutes = GoodNewsDestinations.TOPNEWS_ROUTE,
        icon = Icons.Default.LocalFireDepartment,
        text = R.string.top_news
    ),
    NavigationItemContent(
        goodNewsScreensRoutes = GoodNewsDestinations.LIKES_ROUTE,
        icon = Icons.Default.Favorite,
        text = R.string.liked
    ),
    NavigationItemContent(
        goodNewsScreensRoutes = GoodNewsDestinations.READLATER_ROUTE,
        icon = Icons.Default.Bookmark,
        text = R.string.read_later
    )
)

data class NavigationItemContent(
    val goodNewsScreensRoutes: String,
    val icon: ImageVector,
    val text: Int
)
