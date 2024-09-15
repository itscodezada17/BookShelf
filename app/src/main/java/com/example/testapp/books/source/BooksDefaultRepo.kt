package com.example.testapp.books.source

import com.example.testapp.MyApp
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook
import com.example.testapp.utils.NetworkUtils
import javax.inject.Inject

class BooksDefaultRepo @Inject constructor(
    private val remoteRepo: BooksRemoteRepo,
    private val localRepo: BooksLocalRepo,
    private val context: MyApp
):  DataSource{

    override suspend fun getBooksList(): Result<List<Book>> {
        return if (NetworkUtils.isInternetAvailable(context)){
            remoteRepo.getProductsList()
        }else{
            localRepo.getProductsList()
        }
    }

    override fun getWishList(): Result<List<WishListBook>> {
        return localRepo.getWishList()
    }

    override fun addToCache(books: List<Book>): Result<Boolean> {
        return localRepo.addToCache(books)
    }

    override fun addToWishList(book: WishListBook): Result<Boolean> {
        return localRepo.addToWishList(book)
    }

    override fun removeFromWishList(book: WishListBook): Result<Boolean> {
        return  localRepo.removeFromWishList(book)
    }

}