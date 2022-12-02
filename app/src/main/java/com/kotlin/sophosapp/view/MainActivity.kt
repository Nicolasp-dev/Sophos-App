package com.kotlin.sophosapp.view

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.helpers.Communicator
import com.kotlin.sophosapp.helpers.Constants
import com.kotlin.sophosapp.helpers.Routing


class MainActivity : AppCompatActivity(), Communicator {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.commit {
      replace<LoginFragment>(R.id.frame_container)
      setReorderingAllowed(true)
      addToBackStack(null)
    }
  }

  override fun passData(id: String) {
    Log.i("MAIN ACTIVITY ID", id)
    val bundle = Bundle()
    bundle.putString(Constants.ID_REGISTER, id)
    val detailFragment = DetailDocumentFragment()
    detailFragment.arguments = bundle
    Routing().goTo(this, detailFragment)
  }


}