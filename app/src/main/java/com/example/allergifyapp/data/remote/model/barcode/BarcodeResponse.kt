package com.example.allergifyapp.data.remote.model.barcode

data class BarcodeResponse(
    val name: String? = null,
    val composition: String? = null,
    val nutrition: Nutrition? = null,
    val allergens: List<String>? = null,
)