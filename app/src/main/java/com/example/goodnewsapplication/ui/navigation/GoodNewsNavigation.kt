package com.example.goodnewsapplication.ui.navigation

object GoodNewsDestinations {
    const val HOME_ROUTE = "home"
    const val TOPNEWS_ROUTE = "topnews"
    const val LIKES_ROUTE = "likes"
    const val READLATER_ROUTE = "readlater"
    const val NEWSITEM_ROUTE = "newsitem"
    const val NEWS_ITEM_ID = "newsItemId"
    val NEWS_ROUTE_WITH_ID = "$NEWSITEM_ROUTE/{$NEWS_ITEM_ID}"
    const val WEATHER_ROUTE = "weather"
    const val PROFILE_ROUTE = "profile"
    const val REGISTRATION_ROUTE = "registration"
    const val AUTHENTICATION_ROUTE = "authentication"
}

enum class GoodNewsNavigationType {
    BOTTOM_NAVIGATION,
    NAVIGATION_RAIL,
    PERMANENT_NAVIGATION_DRAWER
}

enum class GoodNewsContentType {
    LIST_ONLY,
    LIST_AND_DETAILS
}