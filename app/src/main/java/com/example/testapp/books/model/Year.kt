package com.example.testapp.books.model

class Year(val name: String, item: List<Book>) {

    val listOfItems: List<Book> = item.toList()

}