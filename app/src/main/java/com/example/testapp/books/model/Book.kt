package com.example.testapp.books.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "books", indices = [Index(value = ["id"], unique = true)])
data class Book(
    @PrimaryKey(autoGenerate = true)
    var bookId: Long,

    var id: String,

    var image: String? = null,

    var score: Double? = null,

    var title: String? = null,

    var publishedChapterDate : Long?    = null
)

@Entity(tableName = "wishlist", indices = [Index(value = ["id"], unique = true)])
data class WishListBook(
    @PrimaryKey(autoGenerate = true)
    var bookId: Long,

    var id: String,

    var image: String? = null,

    var score: Double? = null,

    var title: String? = null,

    var publishedChapterDate : Long?    = null
)
