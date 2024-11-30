package com.example.allergifyapp.ui.forgotpasswordscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityChangePasswordSuccessScreenBinding
import com.example.allergifyapp.ui.loginscreen.LoginScreen
import com.example.allergifyapp.ui.main.BaseActivity

class ChangePasswordSuccessScreen : BaseActivity() {
    private lateinit var binding: ActivityChangePasswordSuccessScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordSuccessScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.closeButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.loginNowButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

    }
}