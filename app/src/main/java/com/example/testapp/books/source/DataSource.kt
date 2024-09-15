package com.example.testapp.books.source

import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook

interface DataSource {
    suspend fun getBooksList(): Result<List<Book>>

    fun getWishList(): Result<List<WishListBook>>

    fun addToCache(books: List<Book>): Result<Boolean>

    fun addToWishList(book: WishListBook): Result<Boolean>

    fun removeFromWishList(book: WishListBook): Result<Boolean>
}