package com.example.allergifyapp.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.allergifyapp.databinding.ActivitySettingBinding
import com.example.allergifyapp.viewmodel.ThemeToggleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val themeToggleViewModel: ThemeToggleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        setupDarkMode()
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.updateProfileButton.setOnClickListener {
            val intent = Intent(this, SettingUserAdjustment::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }
    }

    private fun setupDarkMode() {
        binding.darkModeSwitch.isChecked = themeToggleViewModel.isDarkMode()

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            themeToggleViewModel.setDarkMode(isChecked)

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}