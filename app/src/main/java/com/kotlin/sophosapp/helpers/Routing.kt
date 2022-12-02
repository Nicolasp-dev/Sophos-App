package com.kotlin.sophosapp.helpers

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.view.DocumentsFragment
import com.kotlin.sophosapp.view.MenuFragment
import com.kotlin.sophosapp.view.OfficeFragment
import com.kotlin.sophosapp.view.SendDocumentsFragment

class Routing {
// val context: Context


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
      R.id.op_theme -> {
        goTo(context, MenuFragment())
        true
      }
      else -> {false}
    }
  }

  fun goTo(context: AppCompatActivity, fragment: Fragment) {
    context.supportFragmentManager.commit {
      replace(R.id.frame_container, fragment)
      setReorderingAllowed(true)
      addToBackStack(null)
    }
  }
}

