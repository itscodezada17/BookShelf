package com.example.testapp.books.di

import com.example.testapp.books.source.DataSource
import com.example.testapp.books.source.BooksDefaultRepo
import com.example.testapp.wishlist.source.WishListDataSource
import com.example.testapp.wishlist.source.WishListDefaultRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@InstallIn(ViewModelComponent::class)
@Module
abstract class RepoDiModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteRepo(repo: BooksDefaultRepo): DataSource

//    @Binds
//    @ViewModelScoped
//    abstract fun bindLocalRepo(repo: WishListDefaultRepo): WishListDataSource

}