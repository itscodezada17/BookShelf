package com.example.testapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.auth.model.User

@Dao
interface UserDao {

    // Search User
    // If exists -> LogIn (Search With UserId and Password both) -> boolean
    // else -> SignUp(Insert with UserId, Name, Password and Country)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun signUpUser(user: User)

    @Query("SELECT * FROM users WHERE email =:email and password=:password")
    fun logInUser(email: String, password: String): User

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email =:email and password=:password)")
    fun checkPassword(email: String, password: String): Boolean

    //@Query("SELECT * FROM users WHERE email =:email")
    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email)")
    fun isExistingEmail(email: String): Boolean

    @Delete
    fun removeUser(user: User)

}