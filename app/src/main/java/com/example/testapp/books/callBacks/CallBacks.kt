package com.example.testapp.books.callBacks

import com.example.testapp.books.BooksViewModel
import com.example.testapp.books.model.Book
import timber.log.Timber

class CallBacks(viewModel: BooksViewModel) {
    val vm = viewModel

    fun bookDetail(book: Book){
        Timber.d("Navigate To bookDetail of id: ${book.title}")
    }

    fun addToWishList(book: Book){
        vm.addToWishList(book)
    }

    fun removeFromWishList(book: Book){
        vm.removeFromWishList(book)
    }
}