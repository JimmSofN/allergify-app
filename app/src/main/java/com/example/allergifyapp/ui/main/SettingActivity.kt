package com.example.allergifyapp.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivitySettingBinding
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.viewmodel.AuthViewModel
import com.example.allergifyapp.viewmodel.ThemeToggleViewModel
import com.example.allergifyapp.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val themeToggleViewModel: ThemeToggleViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        setupDarkMode()
        observeUserInfo()
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.updateProfileButton.setOnClickListener {
            val intent = Intent(this, SettingUserAdjustment::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.logoutButton.setOnClickListener {
            authViewModel.logout()
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, IntroScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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

    private fun observeUserInfo() {
        userInfoViewModel.userInfo.observe(this) {
            if (it.isNotEmpty()) {
                val userInfo = it[0]
                binding.ageDataTextView.text = userInfo.age
                binding.heightDataTextView.text = userInfo.height
                binding.weightDataTextView.text = userInfo.weight
            } else {
                binding.ageDataTextView.text = getString(R.string.activity_setting_textview_age_default)
                binding.heightDataTextView.text = getString(R.string.activity_setting_textview_height_default)
                binding.weightDataTextView.text = getString(R.string.activity_setting_textview_weight_default)
            }
        }
    }

}