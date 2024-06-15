package com.example.dermalyze.ui.main.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object Extensions {

    fun ImageView.showImageInto(context: Context, url: String) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}