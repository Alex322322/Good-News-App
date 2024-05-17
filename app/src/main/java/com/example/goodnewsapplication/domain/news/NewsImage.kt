package com.example.goodnewsapplication.domain.news

import androidx.annotation.DrawableRes
import com.example.goodnewsapplication.R

sealed class NewsImage (
    val imageDescription: String,
    @DrawableRes val imageRes: Int
) {
    object imageSpaceman : NewsImage(
        imageDescription = "Spaceman image",
        imageRes = R.drawable.spaceman_image
    )
    object imageSurgery : NewsImage(
        imageDescription = "Surgery image",
        imageRes = R.drawable.surgery_image
    )
    object imageHalfLife : NewsImage(
        imageDescription = "Half Life 3 image",
        imageRes = R.drawable.half_life_3_image
    )
    object imageWeatherTool : NewsImage(
        imageDescription = "Weather climate image",
        imageRes = R.drawable.weather_climate_image
    )
    object imageFiftyYears : NewsImage(
        imageDescription = "Humans image",
        imageRes = R.drawable.fifty_years_image
    )
    object imageSafariPark : NewsImage(
        imageDescription = "Safari park image",
        imageRes = R.drawable.safari_park_image
    )
    object imageOnlineGamesAi : NewsImage(
        imageDescription = "Online games ai image",
        imageRes = R.drawable.online_games_ai_image
    )
    object imagePower : NewsImage(
        imageDescription = "Source of power image",
        imageRes = R.drawable.power_image
    )
    object imageOlympicGames : NewsImage(
        imageDescription = "Olympic games image",
        imageRes = R.drawable.olympic_games_image
    )
    object imageWitcher : NewsImage(
        imageDescription = "Witcher 4 image",
        imageRes = R.drawable.witcher_4_image
    )
    object imageSteam : NewsImage(
        imageDescription = "Steam logo image",
        imageRes = R.drawable.steam_image
    )
    object imageUnrealEngine : NewsImage(
        imageDescription = "Unreal engine logo image",
        imageRes = R.drawable.unreal_engine_image
    )

    companion object {
        fun bindNewsWithImages(id: Int): NewsImage {
            return when(id) {
                1 -> imageSpaceman
                2 -> imageSurgery
                3 -> imageHalfLife
                4 -> imageWeatherTool
                5 -> imageFiftyYears
                6 -> imageSafariPark
                7 -> imageOnlineGamesAi
                8 -> imagePower
                9 -> imageOlympicGames
                10 -> imageWitcher
                11 -> imageSteam
                12 -> imageUnrealEngine
                else -> imageSpaceman
            }
        }
    }
}