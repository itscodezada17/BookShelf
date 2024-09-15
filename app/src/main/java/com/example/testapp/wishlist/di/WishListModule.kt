package com.example.testapp.wishlist.di

import com.example.testapp.wishlist.source.WishListDataSource
import com.example.testapp.wishlist.source.WishListDefaultRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class WishListModule {

    @Binds
    @ViewModelScoped
    abstract fun bindWishListRepo(repo: WishListDefaultRepo): WishListDataSource

}