package com.kotlin.sophosapp.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ThemeTool {
  companion object{
    fun toggleTheme(context: AppCompatActivity): Boolean {
      val theme = UserApp.prefs.getStoreTheme()
      val language = UserApp.prefs.getLanguage()

      if (theme == Constants.LIGHT_THEME) {
        Toast.makeText(context, "TOGGLE TO: DARK MODE", Toast.LENGTH_SHORT).show()

        if(language == "en") UserApp.prefs.storeThemeTitle("Light Mode")
        else UserApp.prefs.storeThemeTitle(Constants.LIGHT_THEME)

        UserApp.prefs.storeTheme(Constants.DARK_THEME)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

      } else {
        Toast.makeText(context, "TOGGLE TO: LIGHT MODE", Toast.LENGTH_SHORT).show()

        if(language == "en") UserApp.prefs.storeThemeTitle("Night Mode")
        else UserApp.prefs.storeThemeTitle(Constants.DARK_THEME)

        UserApp.prefs.storeTheme(Constants.LIGHT_THEME)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      }
      return true
    }
  }
}