package com.example.photoweathertask.base.pick_images

import android.graphics.Bitmap
import java.io.File
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException

fun convertBitmapToFile(destinationFile: File, bitmap: Bitmap) {
    try {
        destinationFile.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()
        val fos = FileOutputStream(destinationFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
