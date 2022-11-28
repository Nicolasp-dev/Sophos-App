package com.kotlin.sophosapp.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kotlin.sophosapp.R

class MyToolbar {

  fun show(activities: AppCompatActivity,toolbar: Toolbar, title: String, upButton: Boolean) {
    activities.setSupportActionBar(toolbar)
    activities.supportActionBar?.title = title
    activities.supportActionBar?.setDisplayHomeAsUpEnabled(upButton)

  }

  // TODO Navigation Controller ...
}