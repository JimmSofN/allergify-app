package com.example.allergifyapp.repository.remote

import com.example.allergifyapp.data.remote.api.ApiServiceGemini
import com.example.allergifyapp.data.remote.model.geminiai.GeminiAiRequest
import com.example.allergifyapp.data.remote.model.geminiai.GeminiAiResponse
import com.example.allergifyapp.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class GeminiApiRepository @Inject constructor(
    private val apiServiceGemini: ApiServiceGemini
){
    suspend fun generateContent(request: GeminiAiRequest): Flow<DataStatus<GeminiAiResponse>> = flow {
        emit(DataStatus.loading())

        val result: Response<GeminiAiResponse> = apiServiceGemini.generateContent(request = request)

        when (result.code()) {
            200 -> {
                emit(DataStatus.success(result.body()))
            }
            400 -> {
                emit(DataStatus.error("Invalid input"))
            }
            401 -> {
                emit(DataStatus.error("Invalid API key"))
            }
            404 -> {
                emit(DataStatus.error("Not Found: The requested resource was not found"))
            }
            else -> {
                emit(DataStatus.error("An error occurred, please try again later"))
            }
        }
    }.catch {
        emit(DataStatus.error("Ups.. something went wrong "))
    }.flowOn(Dispatchers.IO)
}