package com.example.allergifyapp.ui.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.allergifyapp.R
import com.example.allergifyapp.data.local.model.UserName
import com.example.allergifyapp.databinding.FragmentUserProfileScreenBinding
import com.example.allergifyapp.databinding.UserProfileEditDialogBinding
import com.example.allergifyapp.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileScreen : Fragment() {
    private lateinit var binding: FragmentUserProfileScreenBinding
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileScreenBinding.inflate(inflater, container, false)

        setupButton()
        observeUser()
        return binding.root
    }

    private fun setupButton() {
        binding.editButton.setOnClickListener {
            showEditDialog()
        }
    }

    private fun showEditDialog() {
        val addDialogBinding = UserProfileEditDialogBinding.inflate(LayoutInflater.from(context))

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(addDialogBinding.root)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val userNameList = userInfoViewModel.userName.value

        if (!userNameList.isNullOrEmpty()) {
            val currentUserName = userNameList[0].userName
            addDialogBinding.editUserNameEditText.setText(currentUserName)
        } else {
            addDialogBinding.editUserNameEditText.setText("")
        }

        addDialogBinding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        addDialogBinding.userProfileEditSaveButton.setOnClickListener {
            val newUserName = addDialogBinding.editUserNameEditText.text.toString()
            if (newUserName.isNotEmpty()) {
                val userName = UserName(userName = newUserName)

                userInfoViewModel.insertUserName(userName)

                dialog.dismiss()
            } else {
                //TODO
            }
        }

        dialog.show()
    }

    private fun observeUser() {
        userInfoViewModel.userName.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val userInfo = it[0]
                binding.userNameProfile.text = userInfo.userName
            } else {
                binding.userNameProfile.text = getString(R.string.fragment_user_profile_screen_user_name_textview)
            }
        }

        userInfoViewModel.userEmail.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val userEmail = it[0]
                binding.userEmailProfile.text = userEmail.userEmail
            } else {
                binding.userEmailProfile.text = getString(R.string.fragment_user_profile_screen_user_email_textview)
            }
        }
    }
}