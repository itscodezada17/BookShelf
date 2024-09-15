package com.example.testapp.wishlist.callBacks

import com.example.testapp.books.model.Book
import com.example.testapp.wishlist.WishListViewModel
import timber.log.Timber

class WishListCallBacks(viewModel: WishListViewModel) {
    val vm = viewModel

    fun bookDetail(book: Book){
        Timber.d("Navigate To bookDetail of id: ${book.title}")
    }

    fun removeFromWishList(book: Book){
        vm.removeFromWishList(book)
    }
}