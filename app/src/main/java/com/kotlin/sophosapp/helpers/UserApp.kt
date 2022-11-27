package com.kotlin.sophosapp.helpers

import android.app.Application

class UserApp: Application() {
  // Execute every time the app is loaded.

  companion object{
    lateinit var prefs: Prefs
    lateinit var routing: Routing
  }

  override fun onCreate() {
    super.onCreate()
    prefs = Prefs(applicationContext)
    routing = Routing(applicationContext)
  }
}