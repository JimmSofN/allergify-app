package com.example.allergifyapp.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivitySettingBinding
import com.example.allergifyapp.localdata.PreferencesManager

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        setupButton()

        setupDarkMode()

    }

    private fun setupButton() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }
    }

    private fun setupDarkMode() {
        val isDarkMode = preferencesManager.isDarkMode()
        binding.darkModeSwitch.isChecked = isDarkMode

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferencesManager.setDarkMode(isChecked)
            recreate()
        }
    }

}