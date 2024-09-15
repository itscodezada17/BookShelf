package com.example.testapp.auth.logIn.source

import com.example.testapp.auth.model.LogInResponse
import com.example.testapp.auth.model.User
import com.example.testapp.db.BookDataBase
import timber.log.Timber
import javax.inject.Inject

class LogInLocalRepo @Inject constructor(
    room: BookDataBase
) {

    private val service = room.userDao()

    fun loginUser(email: String, password: String): Result<LogInResponse> {
        return try {
            val isCorrectPass = service.checkPassword(email, password)
            val response = if(isCorrectPass){
                 service.logInUser(email, password)
            } else {
                null
            }
            Result.success(LogInResponse(isCorrectPass,response))
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }
}