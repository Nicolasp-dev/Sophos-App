package com.kotlin.sophosapp.utils

import android.util.Log

class NonSuccessResponse {
  fun message(code: Int){
    when (code) {
      400 -> {
        Log.e("Error 400", "Bad Connection")
      }
      404 -> {
        Log.e("Error 404", "Not found")
      }
      else -> {
        Log.e("Error", "Generic Error")
      }
    }
  }
}