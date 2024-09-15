package com.example.testapp.auth.signUp.source

import com.example.testapp.auth.model.User
import com.example.testapp.auth.signUp.Country

interface SignUpDataSource {
    fun checkEmail(email: String): Result<Boolean>
    fun signUpUser(user: User): Result<Boolean>
    suspend fun getCountries(): Result<List<Country>>
    suspend fun getMapCountry(): Result<HashMap<String, String>>
    suspend fun getCurrentCountry(): Result<Country>
}