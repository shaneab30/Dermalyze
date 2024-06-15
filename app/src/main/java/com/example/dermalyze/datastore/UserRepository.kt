package com.example.dermalyze.datastore

import com.example.dermalyze.data.ApiService
import com.example.dermalyze.data.response.LoginResponse

class UserRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun saveToken(token: String) {
        userPreference.saveUserToken(token)
    }

    fun getToken() = userPreference.getUserToken()

    fun getUserPreference(): UserPreference {
        return userPreference
    }
}
