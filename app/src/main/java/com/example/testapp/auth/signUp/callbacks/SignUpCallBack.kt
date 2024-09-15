package com.example.testapp.auth.signUp.callbacks

import com.example.testapp.auth.signUp.SignUpViewModel
import timber.log.Timber

class SignUpCallBack(viewModel: SignUpViewModel) {
    val viewModel = viewModel

    fun updateCountry(country: String){
        viewModel.updateCurrentCountry(country)
    }
}