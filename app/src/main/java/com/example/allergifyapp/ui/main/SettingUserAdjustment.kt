package com.example.allergifyapp.ui.main

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivitySettingUserAdjustmentBinding

class SettingUserAdjustment : BaseActivity() {
    private lateinit var binding: ActivitySettingUserAdjustmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingUserAdjustmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDropdowns()

        setupButton()

    }

    private fun setupDropdowns() {
        val ageItems = (12..44).map { it.toString() }
        val ageAdapter = ArrayAdapter(this, R.layout.list_item, ageItems)
        binding.dropdownAgeAdjustmentField.setAdapter(ageAdapter)

        val heightItems = (90..220).map { it.toString() }
        val heightAdapter = ArrayAdapter(this, R.layout.list_item, heightItems)
        binding.dropdownHeightAdjustmentField.setAdapter(heightAdapter)

        val weightItems = (40..160).map { it.toString() }
        val weightAdapter = ArrayAdapter(this, R.layout.list_item, weightItems)
        binding.dropdownWeightAdjustmentField.setAdapter(weightAdapter)

    }

    private fun setupButton() {
        binding.updateButton.setOnClickListener {
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}