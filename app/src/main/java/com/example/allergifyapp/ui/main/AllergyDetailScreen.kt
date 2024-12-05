package com.example.allergifyapp.ui.main

import android.os.Bundle
import com.example.allergifyapp.databinding.ActivityAllergyDetailScreenBinding

class AllergyDetailScreen : BaseActivity() {
    private lateinit var binding: ActivityAllergyDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergyDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}