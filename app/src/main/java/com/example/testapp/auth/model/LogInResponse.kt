package com.example.testapp.auth.model

data class LogInResponse(
    val status: Boolean,
    val user: User? = null
)
