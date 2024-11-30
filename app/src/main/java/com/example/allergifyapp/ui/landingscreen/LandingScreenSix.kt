package com.example.allergifyapp.ui.landingscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityLandingScreenSixBinding
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.ui.main.BaseActivity

class LandingScreenSix : BaseActivity() {
    private lateinit var binding: ActivityLandingScreenSixBinding
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingScreenSixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
            }
        })

        preferencesManager = PreferencesManager(this)

        setupDropdowns()

        loadSaveData()

        setupButton()

        updateNextButtonState()
    }

    private fun setupDropdowns() {
        val ageItems = (12..44).map { it.toString() }
        val ageAdapter = ArrayAdapter(this, R.layout.list_item, ageItems)
        binding.dropdownAgeField.setAdapter(ageAdapter)

        val heightItems = (90..220).map { it.toString() }
        val heightAdapter = ArrayAdapter(this, R.layout.list_item, heightItems)
        binding.dropdownHeightField.setAdapter(heightAdapter)

        val weightItems = (40..160).map { it.toString() }
        val weightAdapter = ArrayAdapter(this, R.layout.list_item, weightItems)
        binding.dropdownWeightField.setAdapter(weightAdapter)

        binding.dropdownAgeField.setOnItemClickListener { _, _, _, _ -> updateNextButtonState() }
        binding.dropdownHeightField.setOnItemClickListener { _, _, _, _ -> updateNextButtonState() }
        binding.dropdownWeightField.setOnItemClickListener { _, _, _, _ -> updateNextButtonState() }
    }

    private fun loadSaveData() {
        binding.dropdownAgeField.setText(preferencesManager.getUserAge(), false)
        binding.dropdownHeightField.setText(preferencesManager.getUserHeight(), false)
        binding.dropdownWeightField.setText(preferencesManager.getUserWeight(), false)
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            saveData()
            val intent = Intent(this, LandingScreenSeven::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())

        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, LandingScreenFive::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }
    }

    private fun saveData() {
        val userAge = binding.dropdownAgeField.text.toString()
        val userHeight = binding.dropdownHeightField.text.toString()
        val userWeight = binding.dropdownWeightField.text.toString()

        preferencesManager.setUserAge(userAge)
        preferencesManager.setUserHeight(userHeight)
        preferencesManager.setUserWeight(userWeight)
    }

    private fun updateNextButtonState() {
        val userAge = binding.dropdownAgeField.text.toString()
        val userHeight = binding.dropdownHeightField.text .toString()
        val userWeight = binding.dropdownWeightField.text.toString()

        binding.nextButton.isEnabled = userAge.isNotEmpty() && userHeight.isNotEmpty() && userWeight.isNotEmpty()
        binding.nextButton.alpha = if (binding.nextButton.isEnabled) 1f else 0.5f
    }
}