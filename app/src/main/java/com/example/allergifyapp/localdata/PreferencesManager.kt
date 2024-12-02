package com.example.allergifyapp.localdata

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isDarkMode).apply()
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit().putString(KEY_USER_NAME, userName).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun setUserGender(userGender: String) {
        sharedPreferences.edit().putString(KEY_USER_GENDER, userGender).apply()
    }

    fun getUserGender(): String? {
        return sharedPreferences.getString(KEY_USER_GENDER, null)
    }

    fun setUserAge(userAge: String) {
        sharedPreferences.edit().putString(KEY_USER_AGE, userAge).apply()
    }

    fun getUserAge(): String? {
        return sharedPreferences.getString(KEY_USER_AGE, null)
    }

    fun setUserHeight(userHeight: String) {
        sharedPreferences.edit().putString(KEY_USER_HEIGHT, userHeight).apply()
    }

    fun getUserHeight(): String? {
        return sharedPreferences.getString(KEY_USER_HEIGHT, null)
    }

    fun setUserWeight(userWeight: String) {
        sharedPreferences.edit().putString(KEY_USER_WEIGHT, userWeight).apply()
    }

    fun getUserWeight(): String? {
        return sharedPreferences.getString(KEY_USER_WEIGHT, null)
    }

    fun setUserDisease(userDiseases: String) {
        sharedPreferences.edit().putString(KEY_USER_DISEASES, userDiseases).apply()
    }

    fun getUSerDisease(): String? {
        return sharedPreferences.getString(KEY_USER_DISEASES, null)
    }


    companion object {
        private const val KEY_DARK_MODE = "isDarkMode"
        private const val KEY_USER_NAME = "userName"
        private const val KEY_USER_GENDER = "userGender"
        private const val KEY_USER_AGE = "userAge"
        private const val KEY_USER_HEIGHT = "userHeight"
        private const val KEY_USER_WEIGHT = "userWeight"
        private const val KEY_USER_DISEASES = "userDiseases"
    }

}