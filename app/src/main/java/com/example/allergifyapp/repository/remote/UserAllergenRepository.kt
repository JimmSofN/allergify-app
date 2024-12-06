package com.example.allergifyapp.repository.remote

import com.example.allergifyapp.data.remote.api.ApiService
import com.example.allergifyapp.data.remote.model.userallergen.UserAllergen
import com.example.allergifyapp.data.remote.model.userallergenadd.UserAllergenAdd
import com.example.allergifyapp.data.remote.model.userallergenadd.UserAllergenRequest
import com.example.allergifyapp.data.remote.model.userallergendelete.UserAllergenDeleteResponse
import com.example.allergifyapp.data.remote.model.userallergenupdate.UpdateAllergenRequest
import com.example.allergifyapp.data.remote.model.userallergenupdate.UpdateAllergenResponse
import com.example.allergifyapp.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserAllergenRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getUserAllergens(): Flow<DataStatus<List<UserAllergen>>> = flow {
        emit(DataStatus.loading())
        try {
            val response = apiService.getUserAllergens()
            when (response.code()) {
                200 -> {
                    response.body()?.let {
                        emit(DataStatus.success(it.data))
                    } ?: emit(DataStatus.error("No data found"))
                }
                400 -> {
                    emit(DataStatus.error("Bad Request: ${response.message()}"))
                }
                401 -> {
                    emit(DataStatus.error("Unauthorized: ${response.message()}"))
                }
                404 -> {
                    emit(DataStatus.error("Not Found: ${response.message()}"))
                }
                else -> {
                    emit(DataStatus.error("Unexpected error: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            emit(DataStatus.error("Exception: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun updateUserAllergens(id: String, name: String): Flow<DataStatus<UpdateAllergenResponse>> = flow {
        emit(DataStatus.loading())
        try {
            val request = UpdateAllergenRequest(name)
            val response = apiService.updateUserAllergens(id, request)

            when (response.code()) {
                200 -> {
                    response.body()?.let {
                        emit(DataStatus.success(it))
                    } ?: emit(DataStatus.error("No data found"))
                }
                400 -> {
                    emit(DataStatus.error("Bad Request: ${response.message()}"))
                }
                401 -> {
                    emit(DataStatus.error("Unauthorized: ${response.message()}"))
                }
                404 -> {
                    emit(DataStatus.error("Not Found: ${response.message()}"))
                }
                else -> {
                    emit(DataStatus.error("Unexpected error: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            emit(DataStatus.error("Exception: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addUserAllergens(name: String): Flow<DataStatus<UserAllergenAdd>> = flow {
        emit(DataStatus.loading())
        try {
            val response = apiService.addUserAllergens(UserAllergenRequest(name))
            when (response.code()) {
                200 -> {
                    response.body()?.let {
                        emit(DataStatus.success(it.data))
                    } ?: emit(DataStatus.error("No data found"))
                }
                400 -> {
                    emit(DataStatus.error("Bad Request: ${response.message()}"))
                }
                401 -> {
                    emit(DataStatus.error("Unauthorized: ${response.message()}"))
                }
                404 -> {
                    emit(DataStatus.error("Not Found: ${response.message()}"))
                }
                else -> {
                    emit(DataStatus.error("Unexpected error: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            emit(DataStatus.error("Exception: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteUserAllergens(id: String): Flow<DataStatus<UserAllergenDeleteResponse>> = flow {
        emit(DataStatus.loading())
        try {
            val response = apiService.deleteUserAllergens(id)

            when (response.code()) {
                200 -> {
                    response.body()?.let {
                        emit(DataStatus.success(it))
                    } ?: emit(DataStatus.error("No data found"))
                }
                400 -> {
                    emit(DataStatus.error("Bad Request: ${response.message()}"))
                }
                401 -> {
                    emit(DataStatus.error("Unauthorized: ${response.message()}"))
                }
                404 -> {
                    emit(DataStatus.error("Not Found: ${response.message()}"))
                }
                else -> {
                    emit(DataStatus.error("Unexpected error: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            emit(DataStatus.error("Exception: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

}