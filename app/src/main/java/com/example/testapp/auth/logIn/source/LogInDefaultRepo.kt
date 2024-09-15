package com.example.testapp.auth.logIn.source

import com.example.testapp.auth.model.LogInResponse
import com.example.testapp.auth.model.User
import javax.inject.Inject

class LogInDefaultRepo @Inject constructor(
    private val repo: LogInLocalRepo
): LogInDataSource {
    override fun loginUser(email: String, password: String): Result<LogInResponse> {
        return repo.loginUser(email, password)
    }
}