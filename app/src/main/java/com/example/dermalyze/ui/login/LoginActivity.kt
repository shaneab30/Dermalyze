package com.example.dermalyze.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dermalyze.customview.CustomLoginButton
import com.example.dermalyze.databinding.ActivityLoginBinding
import com.example.dermalyze.datastore.Injection
import com.example.dermalyze.ui.main.MainActivity
import com.example.dermalyze.ui.signup.SignUpActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(Injection.provideUserRepository(this), this)
    }

    private lateinit var customLoginButton: CustomLoginButton

    private var emailTextField: TextInputLayout? = null
    private var passwordTextField: TextInputLayout? = null
    private var allFieldsChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //testttt

        customLoginButton = binding.loginButton
        setMyButtonEnable()

        playAnimation()

        emailTextField = binding.email
        passwordTextField = binding.password

        emailTextField?.editText?.addTextChangedListener(createTextWatcher(emailTextField))
        passwordTextField?.editText?.addTextChangedListener(createTextWatcher(passwordTextField))

        // Check if the user is already logged in
        checkIfUserLoggedIn()

        customLoginButton.setOnClickListener {
            allFieldsChecked = checkAllField()

            if (allFieldsChecked) {
                val email = emailTextField?.editText?.text.toString()
                val password = passwordTextField?.editText?.text.toString()
                loginUser(email, password)
            }
        }

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfUserLoggedIn() {
        lifecycleScope.launch {
            loginViewModel.getToken().collect { token ->
                if (!token.isNullOrEmpty()) {
                    navigateToMainActivity()
                }
            }
        }
    }

    private fun setMyButtonEnable() {
        val emailFilled = emailTextField?.editText?.text?.isNotEmpty() == true
        val passwordFilled = passwordTextField?.editText?.text?.isNotEmpty() == true
        customLoginButton.isEnabled = emailFilled && passwordFilled
    }

    private fun loginUser(email: String, password: String) {
        showLoading(true)
        loginViewModel.loginUser(email, password) { response ->
            showLoading(false)
            if (response != null && !response.error!!) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    val token = response.loginResult?.token ?: ""
                    val firstName = response.loginResult?.firstName ?: ""
                    val email = response.loginResult?.email ?: ""

                    Log.d("Bearer Token", "Bearer $token")
                    loginViewModel.saveToken(token)
                    loginViewModel.saveUserDetails(firstName, email)
                    navigateToMainActivity()
                    finish()
                }
            } else {
                Toast.makeText(this, response?.message ?: "Login failed!", Toast.LENGTH_SHORT).show()
                if (response == null) {
                    Log.e("LoginActivity", "Failed to login: response is null")
                } else {
                    Log.e("LoginActivity", "Failed to login: ${response.message}")
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkAllField(): Boolean {
        val emailEditText = emailTextField?.editText
        val passwordEditText = passwordTextField?.editText

        if (emailEditText?.text?.isEmpty() == true) {
            emailTextField?.error = "Email is required"
            return false
        }

        if (passwordEditText?.text?.isEmpty() == true) {
            passwordTextField?.error = "Password is required"
            return false
        }
        return true
    }

    private fun createTextWatcher(field: TextInputLayout?): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                field?.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                setMyButtonEnable()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 0f, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 0f, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.page, View.ALPHA, 0f, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 0f, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 0f, 1f).setDuration(500)
        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }
        AnimatorSet().apply {
            playSequentially(title, email, password, together)
            start()
        }
    }
}
