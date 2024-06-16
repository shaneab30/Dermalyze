package com.example.dermalyze.ui.analyze

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dermalyze.camera.CameraActivity
import com.example.dermalyze.databinding.ActivityResultBinding
import com.example.dermalyze.ui.main.MainActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
            finish()
        }

        val imageUri = intent.getStringExtra(AnalyzeActivity.EXTRA_IMAGE_URI)
        if (imageUri != null) {
            currentImageUri = Uri.parse(imageUri)
            showImage()
        }
    }


    private fun showImage() {
        currentImageUri?.let {
            binding.ivResultImage.setImageURI(it)
        }
    }
}