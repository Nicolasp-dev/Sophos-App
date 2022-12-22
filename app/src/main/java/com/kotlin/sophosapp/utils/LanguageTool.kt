package com.kotlin.sophosapp.utils

import androidx.appcompat.app.AppCompatActivity

class LanguageTool {
  companion object{
    fun toggleLanguage(context: AppCompatActivity): Boolean{
      val language = UserApp.prefs.getLanguage()
      val theme = UserApp.prefs.getStoreTheme()

      if(language == "en" && theme == Constants.LIGHT_THEME){
        UserApp.prefs.storeLanguage("es")
        UserApp.prefs.storeLanguageTitle("Idioma Ingles")
        UserApp.prefs.storeThemeTitle("Modo nocturno")
      }

      if(language == "en" && theme == Constants.DARK_THEME){
        UserApp.prefs.storeLanguage("es")
        UserApp.prefs.storeLanguageTitle("Idioma Ingles")
        UserApp.prefs.storeThemeTitle("Modo dia")
      }

      if(language == "es" && theme == Constants.LIGHT_THEME){
        UserApp.prefs.storeLanguage("en")
        UserApp.prefs.storeLanguageTitle("Spanish Language")
        UserApp.prefs.storeThemeTitle("Night mode")
      }

      if(language == "es" && theme == Constants.DARK_THEME){
        UserApp.prefs.storeLanguage("en")
        UserApp.prefs.storeLanguageTitle("Spanish Language")
        UserApp.prefs.storeThemeTitle("Light Mode")
      }
      context.recreate()
      return true
    }
  }
}