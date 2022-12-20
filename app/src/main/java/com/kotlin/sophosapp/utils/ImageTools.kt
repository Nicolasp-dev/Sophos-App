package com.kotlin.sophosapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageTools {
  companion object{

    fun encodeImage(bm: Bitmap): String {
      val byteArrayOutputStream = ByteArrayOutputStream()
      bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
      val byteArray = byteArrayOutputStream.toByteArray()
      return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decodeImage(image: String): Bitmap? {
      val byteArray: ByteArray = Base64.decode(image, Base64.DEFAULT)
      return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
  }
}