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
import com.kotlin.sophosapp.data.model.theme_state.ThemeState
import com.kotlin.sophosapp.ui.view.DocumentsFragment
import com.kotlin.sophosapp.ui.view.MenuFragment
import com.kotlin.sophosapp.ui.view.OfficeFragment
import com.kotlin.sophosapp.ui.view.SendDocumentsFragment
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs

class Routing {

  fun navigation(context: AppCompatActivity, item: MenuItem, goBack: Boolean = false): Boolean{

    return when(item.itemId){
      android.R.id.home -> {
        if(goBack){
          context.supportFragmentManager.commit{
            context.onBackPressed()
          }
        }else{
          goTo(context, MenuFragment())
        }
        true
      }
      R.id.op_send_docs -> {
        goTo(context, SendDocumentsFragment())
        true
      }
      R.id.op_see_docs -> {
        goTo(context, DocumentsFragment())
        true
      }
      R.id.op_office ->{
        goTo(context, OfficeFragment())
        true
      }
      R.id.op_theme  -> {
        toggleTheme(context, item)
        true
      }
      else -> {false}
    }
  }

  fun goTo(context: AppCompatActivity, fragment: Fragment) {
    context.supportFragmentManager.commit {
      replace(R.id.view_container, fragment)
      setReorderingAllowed(true)
      addToBackStack(null)
    }
  }

  private fun toggleTheme(context: AppCompatActivity, item: MenuItem) {
    val theme = prefs.getStoreTheme()

    if (theme == Constants.LIGHT_THEME) {

      prefs.themeTitle(Constants.LIGHT_THEME)


      Toast.makeText(context, "TOGGLE TO: DARK MODE", Toast.LENGTH_SHORT).show()
      AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

      prefs.storeTheme(Constants.DARK_THEME)

    } else {
      prefs.themeTitle(Constants.DARK_THEME)


      AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
      Toast.makeText(context, "TOGGLE TO: LIGHT MODE", Toast.LENGTH_SHORT).show()

      prefs.storeTheme(Constants.LIGHT_THEME)
    }
  }
}

