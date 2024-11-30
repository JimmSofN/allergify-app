package com.example.allergifyapp.ui.landingscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.databinding.ActivityLandingScreenTwoBinding
import com.example.allergifyapp.ui.main.BaseActivity

class LandingScreenTwo : BaseActivity() {
    private lateinit var binding: ActivityLandingScreenTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingScreenTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
            }
        })

        setupButton()
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, LandingScreenThree::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }
    }
}