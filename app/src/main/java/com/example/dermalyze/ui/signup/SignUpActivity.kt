package com.example.dermalyze.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dermalyze.R
import com.example.dermalyze.customview.CustomButton
import com.example.dermalyze.databinding.ActivitySignUpBinding
import com.example.dermalyze.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var CustomButton: CustomButton

    var firstnameField: TextInputLayout? = null
    var lastnameField: TextInputLayout? = null
    var ageField: TextInputLayout? = null
    var emailField: TextInputLayout? = null
    var passwordField: TextInputLayout? = null


    var allFieldsChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CustomButton = binding.signupButton
        setMyButtonEnable()

        firstnameField = binding.firstname
        lastnameField = binding.lastname
        ageField = binding.age
        emailField = binding.email
        passwordField = binding.password

        firstnameField?.editText?.addTextChangedListener(createTextWatcher(firstnameField))
        lastnameField?.editText?.addTextChangedListener(createTextWatcher(lastnameField))
        ageField?.editText?.addTextChangedListener(createTextWatcher(ageField))
        emailField?.editText?.addTextChangedListener(createTextWatcher(emailField))
        passwordField?.editText?.addTextChangedListener(createTextWatcher(passwordField))


        CustomButton.setOnClickListener {
            allFieldsChecked = checkAllField()

            if (allFieldsChecked) {
                val firstname = firstnameField?.editText?.text.toString()
                val lastname = lastnameField?.editText?.text.toString()
                val age = ageField?.editText?.text.toString()
                val email = emailField?.editText?.text.toString()
                val password = passwordField?.editText?.text.toString()
                registerUser(firstname,lastname,age, email, password)
            }
        }
        playAnimation()
    }

    private fun setMyButtonEnable() {
        val firstnameFilled = firstnameField?.editText?.text?.isNotEmpty() == true
        val lastnameFilled = lastnameField?.editText?.text?.isNotEmpty() == true
        val ageFilled = ageField?.editText?.text?.isNotEmpty() == true
        val emailFilled = emailField?.editText?.text?.isNotEmpty() == true
        val passwordFilled = passwordField?.editText?.text?.isNotEmpty() == true
        CustomButton.isEnabled = firstnameFilled && lastnameFilled && ageFilled && emailFilled && passwordFilled
    }



    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val firstname = ObjectAnimator.ofFloat(binding.firstname, View.ALPHA, 0f, 1f).setDuration(500)
        val lastname = ObjectAnimator.ofFloat(binding.lastname, View.ALPHA, 0f, 1f).setDuration(500)
        val age = ObjectAnimator.ofFloat(binding.age, View.ALPHA, 0f, 1f).setDuration(500)
        val page = ObjectAnimator.ofFloat(binding.page, View.ALPHA, 0f, 1f).setDuration(500)
        val signup =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 0f, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 0f, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 0f, 1f).setDuration(500)
        val together = AnimatorSet().apply {
            playTogether(firstname,lastname,age, email, password)
        }
        AnimatorSet().apply {
            playSequentially(page, together, signup)
            start()
        }
    }

    private fun registerUser(firstname: String, lastname: String, age: String, email: String, password: String) {
        showLoading(true)
        signUpViewModel.registerUser(firstname, lastname, age, email, password) { response ->
            showLoading(false)
            if (response != null && !response.error!!) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    response?.message ?: "Registration failed!",
                    Toast.LENGTH_SHORT
                ).show()
                if (response == null) {
                    Log.e("SignUpActivity", "Failed to register: response is null")
                } else {
                    Log.e("SignUpActivity", "Failed to register: ${response.message}")
                }
            }
        }
    }

    private fun checkAllField(): Boolean {
        val firstnameEditText = firstnameField?.editText
        val lastnameEditText = lastnameField?.editText
        val ageEditText = ageField?.editText
        val emailEditText = emailField?.editText
        val passwordEditText = passwordField?.editText

        if (firstnameEditText?.text?.isEmpty() == true) {
            firstnameField?.error = "fistname is required"
            return false
        }

        if (lastnameEditText?.text?.isEmpty() == true){
            lastnameField?.error = "Lastname is required"
            return false
        }

        if (ageEditText?.text?.isEmpty() == true){
            ageField?.error = "Age is required"
            return false
        }

        if (emailEditText?.text?.isEmpty() == true) {
            emailField?.error = "Email is required"
            return false
        }

        if (passwordEditText?.text?.isEmpty() == true) {
            passwordField?.error = "Password is required"
            return false

        } else if (passwordEditText?.length()!! < 8) {
            passwordField?.error = "Password must be minimum 8 characters"
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
}