package com.example.allergifyapp.data.remote.model.geminiai


import com.google.gson.annotations.SerializedName

data class Candidate(
    val avgLogprobs: Double,
    val content: ContentResponse,
    val finishReason: String
)