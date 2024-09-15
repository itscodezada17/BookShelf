package com.example.testapp.wishlist.source

import com.example.testapp.books.model.WishListBook
import com.example.testapp.db.BookDataBase
import timber.log.Timber
import javax.inject.Inject

class WishListLocalRepo @Inject constructor(
    room: BookDataBase
){
    private val service = room.wishListDao()

     fun getProductsList(): Result<List<WishListBook>> {
        return try {
            val response = service.getWishlistItems()
            Result.success(response)
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