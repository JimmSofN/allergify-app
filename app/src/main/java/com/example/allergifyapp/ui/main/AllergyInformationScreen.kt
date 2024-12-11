package com.example.allergifyapp.ui.main

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allergifyapp.adapter.UserAllergenAdapter
import com.example.allergifyapp.databinding.ActivityAllergyInformationScreenBinding
import com.example.allergifyapp.databinding.UserAllergenInformationAddDialogBinding
import com.example.allergifyapp.utils.DataStatus
import com.example.allergifyapp.viewmodel.UserAllergenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllergyInformationScreen : BaseActivity() {
    private lateinit var binding: ActivityAllergyInformationScreenBinding
    private val userAllergenViewModel: UserAllergenViewModel by viewModels()
    private lateinit var userAllergenAdapter: UserAllergenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergyInformationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()

        setupRecyclerView()
        observeUserAllergens()
        observeUserAllergensUpdate()
        observeSetupAllergensAdd()
        observeSetupAllergensDelete()
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.addButton.setOnClickListener {
            showAddDialog()
        }
    }

    private fun setupRecyclerView() {
        userAllergenAdapter = UserAllergenAdapter(userAllergenViewModel)
        binding.userAllergenRecyclerView.apply {
            adapter = userAllergenAdapter
            layoutManager = LinearLayoutManager(this@AllergyInformationScreen)
        }
    }

    private fun observeUserAllergens() {
        userAllergenViewModel.getUserAllergens()

        userAllergenViewModel.userAllergens.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {

                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { allergens ->
                        userAllergenAdapter.differ.submitList(allergens)
                    }
                }
                DataStatus.Status.ERROR -> {
                    showToast(it.message?: "An unknown error occurred" )
                }
            }
        }
    }

    private fun observeUserAllergensUpdate() {
        userAllergenViewModel.userUpdateAllergens.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    userAllergenViewModel.getUserAllergens()
                }
                DataStatus.Status.ERROR -> {
                }
            }
        }
    }

    private fun observeSetupAllergensAdd() {
        userAllergenViewModel.addUserAllergens.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    userAllergenViewModel.getUserAllergens()
                }
                DataStatus.Status.ERROR -> {
                    showToast(it.message?: "An unknown error occurred" )
                }
            }
        }
    }

    private fun observeSetupAllergensDelete() {
        userAllergenViewModel.deleteUserAllergens.observe(this) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    userAllergenViewModel.getUserAllergens()
                }
                DataStatus.Status.ERROR -> {
                    showToast(it.message?: "An unknown error occurred" )
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAddDialog() {
        val addDialogBinding = UserAllergenInformationAddDialogBinding.inflate(LayoutInflater.from(this))

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(addDialogBinding.root)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addDialogBinding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        addDialogBinding.userAllergenAddButton.setOnClickListener {
            val newAllergenName = addDialogBinding.addUserAllergyEditText.text.toString()
            if (newAllergenName.isNotEmpty()) {
                userAllergenViewModel.addUserAllergens(newAllergenName)
                dialog.dismiss()
            } else {
                //TODO
            }
        }

        dialog.show()
    }
}