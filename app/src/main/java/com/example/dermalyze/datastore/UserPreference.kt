package com.example.dermalyze.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("user_token")
        private val FIRSTNAME = stringPreferencesKey("user_firstname")
        private val EMAIL = stringPreferencesKey("user_email")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getUserToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getFirstName(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[FIRSTNAME]
        }
    }

    suspend fun saveFirstName(firstName: String){
        dataStore.edit { preferences ->
            preferences[FIRSTNAME] = firstName
        }
    }

    fun getEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[EMAIL]
        }
    }
    suspend fun saveEmail(email: String){
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }
}
