package com.example.dermalyze.data

import com.example.dermalyze.data.response.LoginResponse
import com.example.dermalyze.data.response.SignUpResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("user/signup")
    suspend fun signUp(
        @Field("firstName") firstName : String,
        @Field("lastName") lastName : String,
        @Field("age") age: String,
        @Field("email") email : String,
        @Field("password") password : String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}