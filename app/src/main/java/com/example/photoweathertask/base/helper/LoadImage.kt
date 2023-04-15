package com.example.photoweathertask.base.helper

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.photoweathertask.R

object LoadImage {





    fun loadImage(
        context: Context,
        src: String?,
        @DrawableRes placeholder: Int? = R.drawable.weather,
        imageView: ImageView
    ) {
        placeholder?.let {
            Glide.with(context)
                .load(src)
                .placeholder(it)
                .into(imageView)
        } ?: run {
            Glide.with(context)
                .load(src)
                .into(imageView)
        }
    }
}