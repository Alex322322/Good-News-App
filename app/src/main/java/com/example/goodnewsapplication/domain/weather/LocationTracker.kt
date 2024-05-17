package com.example.goodnewsapplication.domain.weather

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}