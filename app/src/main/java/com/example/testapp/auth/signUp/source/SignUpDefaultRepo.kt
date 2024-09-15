package com.example.testapp.auth.signUp.source

import com.example.testapp.auth.model.User
import com.example.testapp.auth.signUp.Country
import javax.inject.Inject

class SignUpDefaultRepo @Inject constructor(
    private val localRepo: SignUpLocalRepo,
    private val remoteRepo: SigUpRemoteRepo
): SignUpDataSource {
    override fun checkEmail(email: String): Result<Boolean> {
        return localRepo.checkUser(email)
    }

    override fun signUpUser(user: User): Result<Boolean> {
        return localRepo.signUp(user)
    }

    override suspend fun getCountries(): Result<List<Country>> {
        return remoteRepo.getCountryList()
    }

    override suspend fun getMapCountry(): Result<HashMap<String, String>> {
        return remoteRepo.getMapCountry()
    }

    override suspend fun getCurrentCountry(): Result<Country> {
        return remoteRepo.getCurrentCountry()
    }
}