package com.example.dermalyze.ui.main.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val publisher: String,
    val datePublish: String,
    val imageUrl: String
) : Parcelable
