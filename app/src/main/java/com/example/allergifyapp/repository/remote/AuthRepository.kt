package com.example.allergifyapp.repository.remote

import com.example.allergifyapp.data.remote.api.ApiService
import com.example.allergifyapp.data.remote.model.login.LoginRequest
import com.example.allergifyapp.data.remote.model.register.RegisterRequest
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) {
    suspend fun register(email: String, password: String) = flow {
        emit(DataStatus.loading())
        val request = RegisterRequest(email, password)
        val result = apiService.register(request)
        when (result.code()) {
            201 -> {
                emit(DataStatus.success(result.body()))
            }
            400 -> {
                emit(DataStatus.error("Email is already registered"))
            }
        }
    }.catch {
        emit(DataStatus.error("Ups.. something went wrong"))
    }.flowOn(Dispatchers.IO)

    suspend fun login(email: String, password: String) = flow {
        emit(DataStatus.loading())
        val request = LoginRequest(email, password)
        val result = apiService.login(request)
        when (result.code()) {
            200 -> {
                val token = result.body()?.token
                if (token != null) {
                    preferencesManager.saveLogin(token)
                }
                emit(DataStatus.success(result.body()))
            }
            400 -> {
                emit(DataStatus.error("Email and password required"))
            }
            401 -> {
                emit(DataStatus.error("User not found"))
            }
            else -> {
                emit(DataStatus.error("An error occurred, please try again later"))
            }
        }
    }.catch {
        emit(DataStatus.error("Ups.. something went wrong"))
    }.flowOn(Dispatchers.IO)

    fun isLoggedIn(): Boolean {
        return preferencesManager.isLoggedIn()
    }

    fun logout() {
        preferencesManager.logout()
    }

}