package com.example.allergifyapp.data.remote.api

import com.example.allergifyapp.data.remote.model.AllergenScan.AllergenResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService2 {
    @Multipart
    @POST("predict")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<AllergenResponse>
}