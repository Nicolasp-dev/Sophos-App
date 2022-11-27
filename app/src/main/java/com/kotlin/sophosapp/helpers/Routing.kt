package com.kotlin.sophosapp.helpers

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.view.DocumentsFragment
import com.kotlin.sophosapp.view.MenuFragment
import com.kotlin.sophosapp.view.OfficeFragment
import com.kotlin.sophosapp.view.SendDocumentsFragment

class Routing (val context: Context) {

  fun navigation(context: AppCompatActivity, item: MenuItem): Boolean{
    return when(item.itemId){
      R.id.op_send_docs -> {
        UserApp.routing.goTo(context, SendDocumentsFragment())
        true
      }
      R.id.op_see_docs -> {
        UserApp.routing.goTo(context, DocumentsFragment())
        true
      }
      R.id.op_office ->{
        UserApp.routing.goTo(context, OfficeFragment())
        true
      }
      R.id.op_theme -> {
        UserApp.routing.goTo(context, MenuFragment())
        true
      }
      else -> {false}
    }
  }

  private fun goTo(context: AppCompatActivity, fragment: Fragment) {
    context.supportFragmentManager.commit {
      replace(R.id.frame_container, fragment)
      setReorderingAllowed(true)
      addToBackStack("replacement")
    }
  }

}