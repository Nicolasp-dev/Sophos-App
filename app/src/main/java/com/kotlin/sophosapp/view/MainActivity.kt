package com.kotlin.sophosapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kotlin.sophosapp.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.commit {
      replace<LoginFragment>(R.id.frame_container)
      setReorderingAllowed(true)
      addToBackStack("replacement")
    }
  }
}