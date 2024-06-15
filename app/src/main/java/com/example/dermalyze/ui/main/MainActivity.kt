package com.example.dermalyze.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dermalyze.R
import com.example.dermalyze.camera.CameraActivity
import com.example.dermalyze.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            Handler(Looper.getMainLooper()).post {
                when (navDestination.id) {
                    R.id.homeFragment, R.id.articleFragment, R.id.historyFragment, R.id.profileFragment, R.id.analyzeActivity-> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.bottomNav.visibility = View.GONE
                    }
                }
            }
        }

        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnNavigationItemSelectedListener {item ->
            when (item.itemId){
                R.id.analyzeActivity -> {
                    val intent = Intent(this,CameraActivity::class.java)
                    startActivity(intent)
                    true
                } else -> {
                    navController.navigate(item.itemId)
                true
                }
            }
        }
    }

    private fun enableEdgeToEdge() {
        // TODO: Implement edge-to-edge logic here if needed
    }
}