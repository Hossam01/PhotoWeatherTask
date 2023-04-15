package com.example.photoweathertask.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import java.io.*
import java.util.*


fun Context.getImageUri(bitmap:Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(this.contentResolver,bitmap, "", null)
    val uri = Uri.parse(path)
    return uri
}

