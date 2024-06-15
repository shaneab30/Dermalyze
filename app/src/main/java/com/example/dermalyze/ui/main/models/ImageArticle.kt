package com.example.dermalyze.ui.main.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageArticle(
    val id: Int,
    val url: String
): Parcelable
