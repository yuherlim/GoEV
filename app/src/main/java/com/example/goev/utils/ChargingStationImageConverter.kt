package com.example.goev.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ChargingStationImageConverter {
    @TypeConverter
    fun convertImage(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun extractImage(profileImage: ByteArray?): Bitmap? {
        return if (profileImage != null && profileImage.isNotEmpty()) {
            BitmapFactory.decodeByteArray(profileImage, 0, profileImage.size)
        } else {
            null
        }
    }
}