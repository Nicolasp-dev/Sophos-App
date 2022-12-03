package com.kotlin.sophosapp.core

import com.kotlin.sophosapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroEngine {
  companion object{
    // ____ [ RETROFIT CONFIG ] ____ //
    fun getRestEngine(): Retrofit {
      return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
  }
}