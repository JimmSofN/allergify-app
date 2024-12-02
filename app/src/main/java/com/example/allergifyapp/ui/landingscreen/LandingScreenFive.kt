package com.example.allergifyapp.ui.landingscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.R
import com.example.allergifyapp.databinding.ActivityLandingScreenFiveBinding
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.ui.main.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingScreenFive : BaseActivity() {
    private lateinit var binding: ActivityLandingScreenFiveBinding

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingScreenFiveBinding.inflate(layoutInflater)
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
        binding.genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val userGender = when (checkedId) {
                R.id.manRadioButton -> "man"
                R.id.womanRadioButton -> "woman"
                else -> null
            }
            userGender?.let {
                preferencesManager.setUserGender(it)
            }
            updateNextButtonState()
        }
    }

    private fun loadSaveData() {
        val savedUserGender = preferencesManager.getUserGender()
        when (savedUserGender) {
            "man" -> binding.manRadioButton.isChecked = true
            "woman" -> binding.womanRadioButton.isChecked = true
        }
    }

    private fun updateNextButtonState() {
        val isGenderSelected = binding.genderRadioGroup.checkedRadioButtonId != -1
        binding.nextButton.isEnabled = isGenderSelected
        binding.nextButton.alpha = if (isGenderSelected) 1f else 0.5f
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, LandingScreenSix::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

}