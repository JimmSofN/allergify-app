package com.example.allergifyapp.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityIntroScreenBinding
import com.example.allergifyapp.ui.landingscreen.LandingScreenOne
import com.example.allergifyapp.ui.loginscreen.LoginScreen

class IntroScreenActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.startButton.setOnClickListener {
            val intent = Intent(this, LandingScreenOne::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }
    }
}