package com.kotlin.sophosapp.helpers

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.sophosapp.R

class MyToolbar {
  fun show(activities: AppCompatActivity, title: String, upButton: Boolean){
    activities.setSupportActionBar(activities.findViewById(R.id.toolbar))
    activities.supportActionBar?.title = title
    activities.supportActionBar?.setDisplayHomeAsUpEnabled(upButton)
  }
}