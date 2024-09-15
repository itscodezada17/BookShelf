package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.db.BookDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): BookDataBase {
        return  Room.databaseBuilder(context,
        BookDataBase::class.java,
        "wishListDB").fallbackToDestructiveMigration()
        .build()
    }
}