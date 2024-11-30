package com.example.allergifyapp.ui.app

import android.app.Application
import com.example.allergifyapp.localdata.PreferencesManager


class MyApplication : Application() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate() {
        super.onCreate()
        preferencesManager = PreferencesManager(this)
    }

    fun isDarkMode(): Boolean {
        return preferencesManager.isDarkMode()
    }

}