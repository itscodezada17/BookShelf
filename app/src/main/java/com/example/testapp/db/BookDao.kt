package com.example.testapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToWishlistBook(book: WishListBook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCache(books: List<Book>)

    @Query("SELECT * FROM books")
    fun getCacheItems(): List<Book>

    @Query("SELECT * FROM wishlist")
    fun getWishlistItems(): List<WishListBook>

    @Delete(WishListBook::class)
    fun removeFromWishList(book: WishListBook)

}