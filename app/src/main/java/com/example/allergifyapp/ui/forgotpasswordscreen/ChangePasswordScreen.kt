package com.example.allergifyapp.ui.forgotpasswordscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityChangePasswordScreenBinding
import com.example.allergifyapp.ui.main.BaseActivity

class ChangePasswordScreen : BaseActivity() {
    private lateinit var binding: ActivityChangePasswordScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, ChangePasswordSuccessScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordOtpScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

    }
}