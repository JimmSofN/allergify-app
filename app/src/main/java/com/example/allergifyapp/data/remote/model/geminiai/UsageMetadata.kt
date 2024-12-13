package com.example.allergifyapp.data.remote.model.geminiai


import com.google.gson.annotations.SerializedName

data class UsageMetadata(
    val candidatesTokenCount: Int,
    val promptTokenCount: Int,
    val totalTokenCount: Int
)