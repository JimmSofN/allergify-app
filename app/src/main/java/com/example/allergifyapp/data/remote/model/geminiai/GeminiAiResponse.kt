package com.example.allergifyapp.data.remote.model.geminiai


import com.google.gson.annotations.SerializedName

data class GeminiAiResponse(
    val candidates: List<Candidate>,
    val modelVersion: String,
    val usageMetadata: UsageMetadata
)