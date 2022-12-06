package com.kotlin.sophosapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.utils.Communicator
import com.kotlin.sophosapp.utils.Constants
import com.kotlin.sophosapp.utils.Routing


class MainActivity : AppCompatActivity(), Communicator {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Routing().goTo(this, LoginFragment())
  }

  override fun passData(id: String) {
    val bundle = Bundle()
    bundle.putString(Constants.ID_REGISTER, id)
    val detailFragment = DetailDocumentFragment()
    detailFragment.arguments = bundle
    Routing().goTo(this, detailFragment)
  }
}