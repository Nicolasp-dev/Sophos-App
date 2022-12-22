package com.kotlin.sophosapp.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class LanguageCtx(base: Context): ContextWrapper(base) {
  companion object{
    fun updateLanguage(ctx: Context, language: String): ContextWrapper {
      var context = ctx
      val config = context.resources.configuration
      val sysLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        getSystemLocale(config)
      } else {
        getSystemLocaleLegacy(config)
      }
      if (language != "" && sysLocale.language != language) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          setSystemLocale(config, locale)
        } else {
          setSystemLocaleLegacy(config, locale)
        }
      }
      context = context.createConfigurationContext(config)
      return LanguageCtx(context)
    }

    @Suppress("DEPRECATION")
    private fun getSystemLocaleLegacy(config: Configuration): Locale {
      return config.locale
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(config: Configuration): Locale {
      return config.locales.get(0)
    }

    @Suppress("DEPRECATION")
    private fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
      config.locale = locale
    }

    private fun setSystemLocale(config: Configuration, locale: Locale) {
      config.setLocale(locale)
    }
  }
}