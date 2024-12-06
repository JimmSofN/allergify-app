package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.remote.model.login.LoginResponse
//import com.example.allergifyapp.data.remote.model.logout.LogoutResponse
import com.example.allergifyapp.data.remote.model.profile.ProfileResponse
import com.example.allergifyapp.data.remote.model.register.RegisterResponse
import com.example.allergifyapp.repository.remote.AuthRepository
import com.example.allergifyapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _registrationStatus = MutableLiveData<DataStatus<RegisterResponse>>()
    val registrationStatus: LiveData<DataStatus<RegisterResponse>>
        get() = _registrationStatus

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registrationStatus.value = DataStatus.loading()
            delay(2000)

            authRepository.register(email, password).collect {
                _registrationStatus.value = it
            }
        }
    }

    private val _loginStatus = MutableLiveData<DataStatus<LoginResponse>>()
    val loginStatus: LiveData<DataStatus<LoginResponse>>
        get() = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginStatus.value = DataStatus.loading()
            delay(2000)

            authRepository.login(email, password).collect {
                _loginStatus.value = it
            }
        }
    }

    private val _profileStatus  = MutableLiveData<DataStatus<ProfileResponse>>()
    val profileStatus: LiveData<DataStatus<ProfileResponse>>
        get() = _profileStatus

    fun profile() {
        viewModelScope.launch {
            authRepository.profile().collect {
                _profileStatus.value = it
            }
        }
    }

//    private val _logoutStatus = MutableLiveData<DataStatus<LogoutResponse>>()
//    val logoutStatus: LiveData<DataStatus<LogoutResponse>> get() = _logoutStatus
//
//    fun logout() {
//        viewModelScope.launch {
//            authRepository.logout().collect {
//                _logoutStatus.value = it
//            }
//        }
//    }

    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

    fun logout() {
        authRepository.logout()
    }

}