package com.kotlin.sophosapp.utils

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.ui.view.DocumentsFragment
import com.kotlin.sophosapp.ui.view.MenuFragment
import com.kotlin.sophosapp.ui.view.OfficeFragment
import com.kotlin.sophosapp.ui.view.SendDocumentsFragment
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs
import kotlin.math.log

class Routing {
  companion object {
    fun navigation(context: AppCompatActivity, item: MenuItem, goBack: Boolean = false): Boolean {

      return when (item.itemId) {

        android.R.id.home -> if (goBack) goBack(context) else goTo(context, MenuFragment())

        R.id.op_send_docs -> goTo(context, SendDocumentsFragment())

        R.id.op_see_docs -> goTo(context, DocumentsFragment())

        R.id.op_office -> goTo(context, OfficeFragment())

        R.id.op_theme -> ThemeTool.toggleTheme(context)

        R.id.op_language -> LanguageTool.toggleLanguage(context)

        R.id.op_logout -> logout(context)

        else -> false
      }
    }

    fun goTo(context: AppCompatActivity, fragment: Fragment): Boolean {
      context.supportFragmentManager.commit {
        replace(R.id.view_container, fragment)
        setReorderingAllowed(true)
        addToBackStack(null)
      }
      return true
    }

    private fun goBack(context: AppCompatActivity): Boolean {
      context.supportFragmentManager.commit {
        context.onBackPressed()
      }
      return true
    }

    private fun logout(context: AppCompatActivity): Boolean {
      context.recreate()
      return true
    }
  }
}



