package com.mit.shop

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayInputStream
import java.io.InputStream

fun decodeBase64ToImageBitmap(base64String: String): ImageBitmap? {
    return try {

        Log.d("API Response", "Received Base64 String: $base64String")


        val cleanBase64String = base64String.substringAfter(",")


        val decodedString: ByteArray = Base64.decode(cleanBase64String, Base64.DEFAULT)
        val inputStream: InputStream = ByteArrayInputStream(decodedString)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap.asImageBitmap()

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}