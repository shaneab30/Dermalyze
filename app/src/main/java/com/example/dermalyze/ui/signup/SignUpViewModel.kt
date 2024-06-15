package com.example.dermalyze.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermalyze.data.ApiConfig
import com.example.dermalyze.data.response.SignUpResponse
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val apiService = ApiConfig.getApiService()

    fun registerUser(firstname: String, lastname: String, age: String, email: String, password: String, onResult: (SignUpResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.signUp(firstname, lastname, age, email, password)
                onResult(response)

            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Unexpeceted error", e)
                onResult(null)
            }
        }
    }

}