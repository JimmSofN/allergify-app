package com.example.allergifyapp.ui.landingscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityLandingScreenSevenBinding
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.ui.main.BaseActivity
import com.example.allergifyapp.ui.registerscreen.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingScreenSeven : BaseActivity() {
    private lateinit var binding: ActivityLandingScreenSevenBinding

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingScreenSevenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
            }
        })


        loadSaveData()

        setupRadioGroup()

        setupButton()

        updateNextButtonState()
    }

    private fun setupRadioGroup() {
        binding.diseaseRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val userDisease = when (checkedId) {
                R.id.noneRadioButton -> "none"
                R.id.lightRadioButton -> "light"
                R.id.hardRadioButton -> "hard"
                else -> null
            }
            userDisease?.let {
                preferencesManager.setUserDisease(it)
            }
            updateNextButtonState()
        }
    }

    private fun loadSaveData() {
        val savedUserDisease = preferencesManager.getUSerDisease()
        when (savedUserDisease) {
            "none" -> binding.noneRadioButton.isChecked = true
            "light" -> binding.lightRadioButton.isChecked = true
            "hard" -> binding.hardRadioButton.isChecked = true
        }
    }

    private fun updateNextButtonState() {
        val isDiseaseSelected = binding.diseaseRadioGroup.checkedRadioButtonId != -1
        binding.nextButton.isEnabled = isDiseaseSelected
        binding.nextButton.alpha = if (isDiseaseSelected) 1f else 0.5f
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }
}