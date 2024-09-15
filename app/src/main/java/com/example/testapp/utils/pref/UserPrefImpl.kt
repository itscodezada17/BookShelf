package com.example.testapp.utils.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.testapp.auth.signUp.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPrefImpl(private val dataStore: DataStore<Preferences>): UserPref {
    override suspend fun isLogIn(): Boolean {
        return  dataStore.data.catch { emit(emptyPreferences()) }.map { preferences ->
            preferences[IS_LOGGEDIN] ?:false
        }.first()
    }

    override suspend fun userEmail(): String {
        return  dataStore.data.catch { emit(emptyPreferences()) }.map { preferences ->
                preferences[EMAIL_KEY] ?:""
            }.first()
    }

    override suspend fun userName(): String {
        return  dataStore.data.catch { emit(emptyPreferences()) }.map { preferences ->
            preferences[NAME_KEY] ?:""
        }.first()
    }

    override suspend fun userCountry(): String {
        return  dataStore.data.catch { emit(emptyPreferences()) }.map { preferences ->
            preferences[COUNTRY_KEY] ?:""
        }.first()
    }

    override suspend fun saveLogIn(email: String, country: String, name: String) {
        dataStore.edit {
            it[EMAIL_KEY] = email
            it[IS_LOGGEDIN] = true
            it[NAME_KEY] = name
            it[COUNTRY_KEY] = country
        }
    }

    override suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }
}