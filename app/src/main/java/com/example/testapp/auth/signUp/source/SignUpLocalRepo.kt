package com.example.testapp.auth.signUp.source

import com.example.testapp.auth.model.User
import com.example.testapp.db.BookDataBase
import timber.log.Timber
import javax.inject.Inject

class SignUpLocalRepo @Inject constructor(
    room: BookDataBase
){
    private val service = room.userDao()

    fun checkUser(email: String): Result<Boolean> {
        return try {
            val response = service.isExistingEmail(email)
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }


    fun signUp(user: User): Result<Boolean> {
        return try {
            service.signUpUser(user)
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }


}