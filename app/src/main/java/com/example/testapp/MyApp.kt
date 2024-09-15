package com.example.testapp

import android.app.Application
import com.example.testapp.books.source.BooksRemoteRepo
import com.example.testapp.wishlist.source.WishListDefaultRepo
import com.example.testapp.wishlist.source.WishListLocalRepo
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    // This will initialize Timber
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }

}