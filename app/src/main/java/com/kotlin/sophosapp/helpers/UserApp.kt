package com.kotlin.sophosapp.helpers

import android.annotation.SuppressLint
import android.app.Application

class UserApp: Application() {
  // Execute every time the app is loaded.




  companion object{
    @SuppressLint("StaticFieldLeak")
    lateinit var prefs: Prefs
    //@SuppressLint("StaticFieldLeak")
    //lateinit var routing: Routing
  }

  override fun onCreate() {
    super.onCreate()
    prefs = Prefs(applicationContext)
    //routing = Routing(applicationContext)
  }
}