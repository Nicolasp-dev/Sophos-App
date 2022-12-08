package com.kotlin.sophosapp.utils

import android.content.Context

class Prefs(val context: Context) {
  val storage = context.getSharedPreferences(Constants.STORAGE, 0)

  fun storeUsername(userName: String){
    storage.edit().putString(Constants.SHARED_USER_NAME, userName).apply()
  }

  fun getUsername(): String{
    return storage.getString(Constants.SHARED_USER_NAME, "")!!
  }

 fun storeUserEmail(userEmail: String){
    storage.edit().putString(Constants.SHARED_USER_EMAIL, userEmail).apply()
  }

  fun getUserEmail(): String{
    return storage.getString(Constants.SHARED_USER_EMAIL, "")!!
  }

  fun storeTheme(theme: String){
    storage.edit().putString(Constants.MAIN_THEME, theme).apply()
  }

  fun getStoreTheme(): String{
    return storage.getString(Constants.MAIN_THEME, Constants.LIGHT_THEME)!!
  }

  fun storeThemeTitle(title: String){
    storage.edit().putString(Constants.MAIN_THEME_TITLE, title).apply()
  }

  fun getThemeTitle(): String{
    return storage.getString(Constants.MAIN_THEME_TITLE, "")!!
  }

}