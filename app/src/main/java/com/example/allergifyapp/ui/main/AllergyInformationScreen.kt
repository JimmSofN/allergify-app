package com.example.allergifyapp.ui.main

import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityAllergyInformationScreenBinding

class AllergyInformationScreen : BaseActivity() {
    private lateinit var binding: ActivityAllergyInformationScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergyInformationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}