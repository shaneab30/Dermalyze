package com.example.dermalyze.ui.analyze

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.dermalyze.camera.CameraActivity
import com.example.dermalyze.databinding.ActivityAnalyzeBinding

class AnalyzeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalyzeBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            startCamera()
        }

//        binding.cameraButton.setOnClickListener {
//            startCamera()
//        }
//        binding.galleryButton.setOnClickListener {
//            startGallery()
//        }

        // Handle the intent from CameraActivity
        val imageUri = intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
        if (imageUri != null) {
            currentImageUri = Uri.parse(imageUri)
            showImage()
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, CameraActivity.CAMERAX_RESULT)
    }

    private fun startGallery() {
        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("TAG", "No Media Selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivImage.setImageURI(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraActivity.CAMERAX_RESULT && resultCode == RESULT_OK) {
            val imageUri = data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
            if (imageUri != null) {
                currentImageUri = Uri.parse(imageUri)
                showImage()
            }
        }
    }
}
