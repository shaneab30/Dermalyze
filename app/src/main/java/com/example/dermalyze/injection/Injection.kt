package com.example.dermalyze.datastore

import android.content.Context
import com.example.dermalyze.data.ApiConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val dataStore = context.dataStore
        val userPreference = UserPreference.getInstance(dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService, userPreference)
    }
}
