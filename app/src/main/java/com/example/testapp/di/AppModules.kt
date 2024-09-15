package com.example.testapp.di

import android.content.Context
import com.example.testapp.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityRetainedComponent::class)
@Module
object AppModules {
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApp {
        return app as MyApp
    }
}