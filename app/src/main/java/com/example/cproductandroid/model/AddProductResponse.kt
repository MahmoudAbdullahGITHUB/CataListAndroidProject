package com.example.cproductandroid.model

data class AddProductResponse(
    val isSuccess: Boolean,
    val message: String,
    val response: AddResponse
)



data class AddResponse(
    val message: String
)