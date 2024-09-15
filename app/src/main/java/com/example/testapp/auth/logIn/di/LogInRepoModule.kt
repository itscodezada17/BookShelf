package com.example.testapp.auth.logIn.di

import com.example.testapp.auth.logIn.source.LogInDataSource
import com.example.testapp.auth.logIn.source.LogInDefaultRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class LogInRepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLogInRepo(repo: LogInDefaultRepo): LogInDataSource

}