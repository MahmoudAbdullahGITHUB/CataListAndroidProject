package com.example.cproductandroid.model

data class ImageResponse(
    val isSuccess: Boolean,
    val message: String,
    val response: ImResponseChild
)

data class ImResponseChild(
    val exceptionMessage: String,
    val exceptionStackTrace: String
)