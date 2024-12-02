package com.example.allergifyapp.viewmodel


import androidx.lifecycle.ViewModel
import com.example.allergifyapp.localdata.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeToggleViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun setDarkMode(isDarkMode: Boolean) {
        preferencesManager.setDarkMode(isDarkMode)
    }

    fun isDarkMode(): Boolean {
        return preferencesManager.isDarkMode()
    }

}