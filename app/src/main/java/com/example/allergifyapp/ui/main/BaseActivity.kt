package com.example.allergifyapp.ui.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.allergifyapp.R
import com.example.allergifyapp.ui.app.MyApplication

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val app = application as MyApplication
        if (app.isDarkMode()) {
            setTheme(R.style.AppTheme_dark)

        } else {
            setTheme(R.style.AppTheme_light)
        }
        super.onCreate(savedInstanceState)

        // Set FLAG_LAYOUT_NO_LIMIT
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}