package com.example.testapp.auth.signUp.di

import com.example.testapp.auth.signUp.source.SignUpDataSource
import com.example.testapp.auth.signUp.source.SignUpDefaultRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SignUpRepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpRepo(signUpDefaultRepo: SignUpDefaultRepo): SignUpDataSource

}