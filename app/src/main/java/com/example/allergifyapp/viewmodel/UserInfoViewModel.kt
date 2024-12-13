package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.local.model.UserEmail
import com.example.allergifyapp.data.local.model.UserInfo
import com.example.allergifyapp.data.local.model.UserName
import com.example.allergifyapp.repository.local.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    val userInfo: LiveData<List<UserInfo>> = userInfoRepository.getUserInfo()

    fun insertUserInfo(userInfo: UserInfo) {
        viewModelScope.launch {
            userInfoRepository.insertOrUpdateUserInfo(userInfo)
        }
    }

    val userName: LiveData<List<UserName>> = userInfoRepository.getUserName()

    fun insertUserName(userName: UserName) {
        viewModelScope.launch {
            userInfoRepository.insertOrUpdateUserName(userName)
        }
    }

    val userEmail: LiveData<List<UserEmail>> = userInfoRepository.getUserEmail()

    fun insertUserEmail(userEmail: UserEmail) {
        viewModelScope.launch {
            userInfoRepository.insertOrUpdateUserEmail(userEmail)
        }
    }
}