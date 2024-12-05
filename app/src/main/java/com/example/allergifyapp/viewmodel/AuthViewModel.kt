package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.remote.model.login.LoginResponse
import com.example.allergifyapp.data.remote.model.register.RegisterResponse
import com.example.allergifyapp.repository.remote.AuthRepository
import com.example.allergifyapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _registrationStatus = MutableLiveData<DataStatus<RegisterResponse>>()
    val registrationStatus: LiveData<DataStatus<RegisterResponse>>
        get() = _registrationStatus

    fun register(email: String, password: String) {
        viewModelScope.launch {
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
            authRepository.login(email, password).collect {
                _loginStatus.value = it
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

}