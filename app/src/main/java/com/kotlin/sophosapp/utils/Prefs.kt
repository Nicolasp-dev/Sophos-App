package com.kotlin.sophosapp.utils

import android.content.Context

class Prefs(val context: Context) {
  val storage = context.getSharedPreferences(Constants.STORAGE, 0)

  fun storeUsername(userName: String){
    storage.edit().putString(Constants.SHARED_USER_NAME, userName).apply()
  }

 fun storeUserEmail(userEmail: String){
    storage.edit().putString(Constants.SHARED_USER_EMAIL, userEmail).apply()
  }

  fun getUsername(): String{
    return storage.getString(Constants.SHARED_USER_NAME, "")!!
  }

  fun getUserEmail(): String{
    return storage.getString(Constants.SHARED_USER_EMAIL, "")!!
  }

}