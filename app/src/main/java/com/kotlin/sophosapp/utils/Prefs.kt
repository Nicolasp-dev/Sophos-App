package com.kotlin.sophosapp.utils

import android.content.Context

class Prefs(val context: Context) {
  val storage = context.getSharedPreferences(Constants.STORAGE, 0)

  // User Name Prefs.
  fun storeUsername(userName: String) {
    storage.edit().putString(Constants.SHARED_USER_NAME, userName).apply()
  }

  fun getUsername(): String {
    return storage.getString(Constants.SHARED_USER_NAME, "")!!
  }

  // User Email Prefs.
  fun storeUserEmail(userEmail: String ){
    storage.edit().putString(Constants.SHARED_USER_EMAIL, userEmail).apply()
  }

  fun getUserEmail(): String {
    return storage.getString(Constants.SHARED_USER_EMAIL, "")!!
  }

  // Theme Prefs.
  fun storeTheme(theme: String) {
    storage.edit().putString(Constants.MAIN_THEME, theme).apply()
  }

  fun getStoreTheme(): String {
    return storage.getString(Constants.MAIN_THEME, Constants.LIGHT_THEME)!!
  }

  // Theme Title Prefs.
  fun storeThemeTitle(title: String) {
    storage.edit().putString(Constants.MAIN_THEME_TITLE, title).apply()
  }

  fun getThemeTitle(): String {
    return storage.getString(Constants.MAIN_THEME_TITLE, "Modo nocturno")!!
  }

  // Language Prefs.
  fun storeLanguage(language: String) {
    storage.edit().putString(Constants.MAIN_LANGUAGE, language).apply()
  }

  fun getLanguage(): String{
    return storage.getString(Constants.MAIN_LANGUAGE, "es")!!
  }

  // Language Title Prefs.
  fun storeLanguageTitle(title: String) {
    storage.edit().putString(Constants.MAIN_LANGUAGE_TITLE, title).apply()
  }

  fun getLanguageTitle(): String {
    return storage.getString(Constants.MAIN_LANGUAGE_TITLE, "Idioma ingles")!!
  }
}