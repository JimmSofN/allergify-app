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
                emit(DataStatus.error(result.message()))
            }
            else -> {
                emit(DataStatus.error("Unexpected error: ${result.message()}"))
            }
        }
    }.catch {
            emit(DataStatus.error(it.message ?: "An unknown error occurred"))
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
                emit(DataStatus.error(result.message()))
            }
            401 -> {
                emit(DataStatus.error(result.message()))
            }
            else -> {
                emit(DataStatus.error("Unexpected error: ${result.message()}"))
            }
        }
    }.catch {
        emit(DataStatus.error(it.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    suspend fun profile() = flow {
        emit(DataStatus.loading())

        val result = apiService.profile()
        when(result.code()) {
            200 -> {
                emit(DataStatus.success(result.body()))
            }
            400 -> {
                emit(DataStatus.error(result.message()))
            }
            401 -> {
                emit(DataStatus.error(result.message()))
            }
            else -> {
                emit(DataStatus.error("Unexpected error: ${result.message()}"))
            }
        }
    } .catch {
        emit(DataStatus.error(it.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

//    suspend fun logout() = flow {
//        emit(DataStatus.loading())
//        val result = apiService.logout()
//        when (result.code()) {
//            200 -> {
//                preferencesManager.logout()
//                emit(DataStatus.success(result.body()))
//            }
//            401 -> {
//                emit(DataStatus.error(result.message()))
//            }
//            else -> {
//                emit(DataStatus.error("Logout failed: ${result.message()}"))
//            }
//        }
//    }.catch {
//        emit(DataStatus.error(it.message ?: "An unknown error occurred"))
//    }.flowOn(Dispatchers.IO)

    fun isLoggedIn(): Boolean {
        return preferencesManager.isLoggedIn()
    }

    fun logout() {
        preferencesManager.logout()
    }

}