package com.example.allergifyapp.ui.forgotpasswordscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityForgotPasswordOtpScreenBinding
import com.example.allergifyapp.ui.main.BaseActivity

class ForgotPasswordOtpScreen : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordOtpScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordOtpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, ChangePasswordScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordOtpScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

    }

}