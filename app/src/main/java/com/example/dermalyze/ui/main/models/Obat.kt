package com.example.dermalyze.ui.main.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Obat(
    val id: Int,
    val title: String,
    val size: String,
    val readme: String,
    val description: String,
    val treatment: String,
    val imageUrl: String
) : Parcelable