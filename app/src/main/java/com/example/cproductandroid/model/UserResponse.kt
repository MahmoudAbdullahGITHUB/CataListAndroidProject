package com.example.cproductandroid.model


data class ResponseUser (
    val isSuccess: Boolean,
    val message: String,
    val response: UserResponse
)

data class UserResponse(
    val user: User
)

data class User(
    val fullName: String,
    val refreshToken: String,
    val token: String,
    val userEmail: String,
    val userId: Int,
    val userTelephone: String
)
