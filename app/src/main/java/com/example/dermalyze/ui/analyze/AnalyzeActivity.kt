package com.example.dermalyze.ui.analyze

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dermalyze.databinding.ActivityAnalyzeBinding

class AnalyzeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalyzeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}