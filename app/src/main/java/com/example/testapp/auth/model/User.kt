package com.example.testapp.auth.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "users", indices = [Index(value = ["userId"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Long? = null,

    var email: String? = null,

    var name: String? = null,

    var password: String? = null,

    var country: String? = null
)