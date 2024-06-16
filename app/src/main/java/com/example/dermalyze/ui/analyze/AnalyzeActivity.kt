package com.example.dermalyze.ui.analyze

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

        binding.btnAnalyze.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            currentImageUri?.let {
                intent.putExtra(EXTRA_IMAGE_URI, it.toString())
            }
            startActivity(intent)
        }


        // Handle the intent from CameraActivity
        val imageUri = intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
        if (imageUri != null) {
            currentImageUri = Uri.parse(imageUri)
            showImage()
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

    companion object {
        const val EXTRA_IMAGE_URI ="Analyze Image"
    }
}
