package com.example.goodnewsapplication

import android.app.Application
import com.example.goodnewsapplication.di.AppContainer
import com.example.goodnewsapplication.di.DefaultAppContainer

class GoodNewsApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}