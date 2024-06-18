package com.example.dermalyze.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SecondApiConfig {
    private const val BASE_URL = "http://34.50.68.30:8080/"

    fun getApiService(): SecondApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val customInterceptor = Interceptor { chain ->
            val request = chain.request()
            val requestHeaders = request.headers
            Log.d("API Request", "URL: ${request.url}")
            Log.d("API Request", "Headers: $requestHeaders")
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(customInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(SecondApiService::class.java)
    }
}
