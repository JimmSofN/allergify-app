package com.example.allergifyapp.ui.loginscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityLoginScreenBinding
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.ui.main.IntroScreenActivity

class LoginScreen : BaseActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.loginWithEmailButton.setOnClickListener {
            val intent = Intent(this, LoginEmailScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, IntroScreenActivity::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

    }
}