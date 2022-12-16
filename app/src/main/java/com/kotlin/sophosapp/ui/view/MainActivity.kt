package com.kotlin.sophosapp.ui.view

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.utils.Communicator
import com.kotlin.sophosapp.utils.Constants
import com.kotlin.sophosapp.utils.LanguageCtx
import com.kotlin.sophosapp.utils.Routing
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs


class MainActivity : AppCompatActivity(), Communicator {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Routing().goTo(this, LoginFragment())
  }

  override fun attachBaseContext(newBase: Context) {

    val localeToSwitchTo = prefs.getLanguage()

    val localeUpdateContext: ContextWrapper = LanguageCtx.updateLanguage(newBase, localeToSwitchTo)

    super.attachBaseContext(localeUpdateContext)
  }

  override fun passData(id: String) {
    val bundle = Bundle()
    bundle.putString(Constants.ID_REGISTER, id)
    val detailFragment = DetailDocumentFragment()
    detailFragment.arguments = bundle
    Routing().goTo(this, detailFragment)
  }
}