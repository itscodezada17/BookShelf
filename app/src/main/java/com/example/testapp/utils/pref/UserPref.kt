package com.example.testapp.utils.pref

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


val IS_LOGGEDIN = booleanPreferencesKey("isLoggedIn")
val EMAIL_KEY = stringPreferencesKey("email")
val NAME_KEY = stringPreferencesKey("name")
val COUNTRY_KEY = stringPreferencesKey("country")


interface UserPref {

    suspend fun isLogIn(): Boolean

    suspend fun userEmail(): String

    suspend fun userName(): String

    suspend fun userCountry(): String

    suspend fun saveLogIn(email: String, country: String, name: String)

    suspend fun clearData()
}