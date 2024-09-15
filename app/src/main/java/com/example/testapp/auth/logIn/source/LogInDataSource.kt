package com.example.testapp.auth.logIn.source

import com.example.testapp.auth.model.LogInResponse
import com.example.testapp.auth.model.User

interface LogInDataSource {
    fun loginUser(email: String, password: String): Result<LogInResponse>
}