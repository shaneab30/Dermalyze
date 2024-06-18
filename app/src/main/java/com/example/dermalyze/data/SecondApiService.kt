package com.example.dermalyze.data

import com.example.dermalyze.data.response.AnalyzeResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SecondApiService {

    @POST("predict")
    @Multipart
    suspend fun analyze(
        @Part file: MultipartBody.Part
    ): AnalyzeResponse
}