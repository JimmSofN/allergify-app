package com.example.allergifyapp.data.remote.api


import com.example.allergifyapp.data.remote.model.geminiai.GeminiAiRequest
import com.example.allergifyapp.data.remote.model.geminiai.GeminiAiResponse
import com.example.allergifyapp.utils.Constant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceGemini {

    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    suspend fun generateContent(
        @Query("apiKey") apiKey: String = Constant.API_KEY,
        @Body request: GeminiAiRequest
    ): Response<GeminiAiResponse>

}