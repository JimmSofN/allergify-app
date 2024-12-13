package com.example.allergifyapp.repository.remote

import com.example.allergifyapp.data.remote.api.ApiService2
import com.example.allergifyapp.data.remote.model.AllergenScan.AllergenResponse
import com.example.allergifyapp.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class AllergyScanRepository @Inject constructor(
    private val apiService: ApiService2
) {
    suspend fun uploadImage(imagePart: MultipartBody.Part): Flow<DataStatus<AllergenResponse>> = flow {
        emit(DataStatus.loading())

        val result: Response<AllergenResponse> = apiService.uploadImage(imagePart)

        when (result.code()) {
            200 -> {
                val allergenResponse = result.body()
                emit(DataStatus.success(allergenResponse))
            }
            400 -> {
                emit(DataStatus.error("Invalid image format"))
            }
            404 -> {
                emit(DataStatus.error("Image not found"))
            }
            else -> {
                emit(DataStatus.error("An error occurred, please try again later"))
            }
        }
    }.catch { exception ->
        emit(DataStatus.error("Ups.. something went wrong: ${exception.message}"))
    }.flowOn(Dispatchers.IO)
}