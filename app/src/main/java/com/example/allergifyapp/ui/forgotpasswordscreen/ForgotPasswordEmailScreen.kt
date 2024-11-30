package com.example.allergifyapp.ui.forgotpasswordscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityForgotPasswordEmailScreenBinding
import com.example.allergifyapp.ui.loginscreen.LoginEmailScreen
import com.example.allergifyapp.ui.loginscreen.LoginScreen
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.ui.main.MainActivity

class ForgotPasswordEmailScreen : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordEmailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordEmailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordOtpScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginEmailScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

    }
}