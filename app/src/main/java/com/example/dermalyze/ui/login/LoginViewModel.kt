package com.example.dermalyze.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermalyze.data.response.LoginResponse
import com.example.dermalyze.datastore.Injection
import com.example.dermalyze.datastore.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModel() {

    fun loginUser(email: String, password: String, onResult: (LoginResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(email, password)
                if (!response.error!! && response.loginResult != null) {
                    response.loginResult.token?.let { userRepository.saveToken(it) }
                }
                onResult(response)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            userRepository.saveToken(token)
        }
    }

    fun getToken(): Flow<String?> {
        return userRepository.getToken()
    }

    fun saveUserDetails(firstName: String, email: String) {
        viewModelScope.launch {
            val userPreference = Injection.provideUserRepository(context).getUserPreference()
            userPreference.saveFirstName(firstName)
            userPreference.saveEmail(email)
        }
    }
}
