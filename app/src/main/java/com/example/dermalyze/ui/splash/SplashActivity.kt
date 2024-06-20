package com.example.dermalyze.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dermalyze.R
import com.example.dermalyze.datastore.Injection
import com.example.dermalyze.ui.login.LoginActivity
import com.example.dermalyze.ui.main.MainActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Check if the user is already logged in
        lifecycleScope.launch {
            val userPreference = Injection.provideUserRepository(this@SplashActivity).getUserPreference()
            val token = userPreference.getUserToken().firstOrNull()

            if (!token.isNullOrEmpty()) {
                navigateToMainActivity()
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToLoginActivity()
                }, 3000)
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
