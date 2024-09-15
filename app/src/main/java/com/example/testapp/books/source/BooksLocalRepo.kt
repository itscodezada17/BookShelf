package com.example.testapp.books.source

import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook
import com.example.testapp.db.BookDataBase
import timber.log.Timber
import javax.inject.Inject

class BooksLocalRepo @Inject constructor(
    room: BookDataBase
) {
    private val service = room.wishListDao()

    fun getProductsList(): Result<List<Book>> {
        return try {
            val response = service.getCacheItems()
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

    fun getWishList(): Result<List<WishListBook>> {
        return try {
            val response: List<WishListBook> = service.getWishlistItems()
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

     fun addToWishList(book: WishListBook): Result<Boolean> {
        return try {
            service.addToWishlistBook(book)
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }



    fun addToCache(books: List<Book>): Result<Boolean> {
        return try {
            service.addToCache(books)
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

    fun removeFromWishList(book: WishListBook): Result<Boolean> {
        return try {
            service.removeFromWishList(book)
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }
}
