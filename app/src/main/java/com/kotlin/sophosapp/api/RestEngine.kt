package com.kotlin.sophosapp.api

import com.kotlin.sophosapp.helpers.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {
  companion object{
    // DEFAULT RETROFIT CONFIG
    fun getRestEngine(): Retrofit {
      return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
  }
}