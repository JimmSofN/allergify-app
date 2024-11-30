package com.example.allergifyapp.ui.landingscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.OnBackPressedCallback
import com.example.allergifyapp.databinding.ActivityLandingScreenFourBinding
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.ui.main.BaseActivity

class LandingScreenFour : BaseActivity() {
    private lateinit var binding: ActivityLandingScreenFourBinding
    private lateinit var preferencesManager: PreferencesManager
    private var isUserInteracted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingScreenFourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
            }
        })

        preferencesManager = PreferencesManager(this)

        loadSaveData()

        setupButton()

        updateNextButtonState()
    }

    private fun saveData() {
        val userName = binding.userNameEditText.text.toString()
        preferencesManager.setUserName(userName)
    }

    private fun loadSaveData() {
        val savedUserName = preferencesManager.getUserName()
        binding.userNameEditText.setText(savedUserName)
    }

    private fun setupButton() {
        binding.nextButton.setOnClickListener {
            saveData()

            val intent = Intent(this, LandingScreenFive::class.java)
            startActivity(intent, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
        }

        binding.userNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isUserInteracted = true
            }

            override fun afterTextChanged(s: Editable) {
                updateNextButtonState()
            }
        })
    }

    private fun updateNextButtonState() {
        val userName = binding.userNameEditText.text.toString()
        if (userName.isEmpty()) {
            binding.nextButton.isEnabled = false
            binding.nextButton.alpha = 0.5f
        } else {
            binding.nextButton.isEnabled = true
            binding.nextButton.alpha = 1f
        }
    }
}