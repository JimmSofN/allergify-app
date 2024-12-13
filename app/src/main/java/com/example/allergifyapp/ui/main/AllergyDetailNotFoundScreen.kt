package com.example.allergifyapp.ui.main

import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityAllergyDetailNotFoundScreenBinding

class AllergyDetailNotFoundScreen : BaseActivity() {
    private lateinit var binding: ActivityAllergyDetailNotFoundScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergyDetailNotFoundScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}