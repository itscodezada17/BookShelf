package com.example.testapp.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.auth.model.User
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook

@Database(entities = [Book::class, WishListBook::class, User::class], version = 6)
abstract class BookDataBase: RoomDatabase() {

    abstract fun wishListDao(): BookDao
    abstract fun userDao(): UserDao
}