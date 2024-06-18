package com.example.dermalyze.ui.analyze

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dermalyze.camera.CameraActivity
import com.example.dermalyze.camera.createCustomTempFile
import com.example.dermalyze.data.SecondApiConfig
import com.example.dermalyze.data.response.AnalyzeResponse
import com.example.dermalyze.databinding.ActivityAnalyzeBinding
import com.example.dermalyze.datastore.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

data class MultipartData(
    val filePart: MultipartBody.Part
)

class AnalyzeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalyzeBinding
    private var currentImageUri: Uri? = null
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize userPreference if needed
        // userPreference = UserPreference.getInstance(this)

        binding.btnAnalyze.setOnClickListener {
            analyzeImage()
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

    private fun analyzeImage() {
        Log.d("AnalyzeActivity", "analyzeImage called")
        if (currentImageUri != null) {
            val multipartData = createMultipart(currentImageUri!!, this)
            if (multipartData == null) {
                Log.e("AnalyzeActivity", "Failed to create multipart data")
                Toast.makeText(this, "Failed to prepare image for analysis", Toast.LENGTH_LONG)
                    .show()
                return
            }

            val (filePart) = multipartData
            Log.d("AnalyzeActivity", "Multipart data created: ${filePart.body.contentType()}")

            binding.progressBar.visibility = android.view.View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val secondApiService = SecondApiConfig.getApiService()
                    val response = secondApiService.analyze(filePart)
                    Log.d("AnalyzeActivity", "API call successful: $response")
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = android.view.View.GONE
                        handleApiResponse(response)
                    }
                } catch (e: HttpException) {
                    Log.e("AnalyzeActivity", "API call failed: HTTP ${e.code()} ${e.message()}", e)
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e("AnalyzeActivity", "Error body: $errorBody")
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = android.view.View.GONE
                        Toast.makeText(
                            this@AnalyzeActivity,
                            "Failed to analyze image: HTTP ${e.code()} ${e.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.e("AnalyzeActivity", "API call failed: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = android.view.View.GONE
                        Toast.makeText(
                            this@AnalyzeActivity,
                            "Failed to analyze image: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else {
            Log.w("AnalyzeActivity", "currentImageUri is null")
        }
    }


    private fun handleApiResponse(response: AnalyzeResponse) {
        Log.d("AnalyzeActivity", "handleApiResponse called with response: $response")
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(EXTRA_ANALYZE_RESPONSE, response)
        intent.putExtra(EXTRA_IMAGE_URI, currentImageUri.toString())
        startActivity(intent)
    }

    private fun createMultipart(
        fileUri: Uri,
        context: Context
    ): MultipartData {
        val contentResolver = context.contentResolver

        val file = createCustomTempFile(context)

        contentResolver.openInputStream(fileUri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val reqFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
        return MultipartData(body)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraActivity.CAMERAX_RESULT && resultCode == RESULT_OK) {
            val imageUri = data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
            if (imageUri != null) {
                currentImageUri = Uri.parse(imageUri)
                // Grant URI permission
                contentResolver.takePersistableUriPermission(
                    currentImageUri!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                showImage()
            }
        }
    }


    companion object {
        const val EXTRA_IMAGE_URI = "Analyze Image"
        const val EXTRA_ANALYZE_RESPONSE = "Analyze Response"
    }
}
