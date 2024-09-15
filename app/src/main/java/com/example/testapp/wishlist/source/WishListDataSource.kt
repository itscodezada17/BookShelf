package com.example.testapp.wishlist.source

import com.example.testapp.books.model.WishListBook

interface WishListDataSource {
     fun getBooksList(): Result<List<WishListBook>>
     fun removeFromWishList(book: WishListBook): Result<Boolean>
}