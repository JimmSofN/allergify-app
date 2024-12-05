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

    // Auth
    fun saveLogin(token: String) {
        sharedPreferences.edit().apply {
            putString(KEY_TOKEN, token)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun logout() {
        sharedPreferences.edit().apply {
            remove(KEY_TOKEN)
            putBoolean(KEY_IS_LOGGED_IN, false)
            apply()
        }
    }

    companion object {
        private const val KEY_DARK_MODE = "isDarkMode"
        private const val KEY_USER_NAME = "userName"
        private const val KEY_USER_GENDER = "userGender"
        private const val KEY_USER_AGE = "userAge"
        private const val KEY_USER_HEIGHT = "userHeight"
        private const val KEY_USER_WEIGHT = "userWeight"
        private const val KEY_USER_DISEASES = "userDiseases"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

}