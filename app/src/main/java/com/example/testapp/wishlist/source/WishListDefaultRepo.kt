package com.example.testapp.wishlist.source

import com.example.testapp.books.model.WishListBook
import javax.inject.Inject

class WishListDefaultRepo @Inject constructor(
    private val localRepo: WishListLocalRepo
):  WishListDataSource{

    override  fun getBooksList(): Result<List<WishListBook>> {
        return  localRepo.getProductsList()
    }


    override fun removeFromWishList(book: WishListBook): Result<Boolean> {
        return  localRepo.removeFromWishList(book)
    }

}