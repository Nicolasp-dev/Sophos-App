package com.kotlin.sophosapp.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Dialog {
  companion object{
    fun showAlertDialogPermission(context: AppCompatActivity){
      AlertDialog.Builder(context)
        .setMessage( "It looks like you have turned off permissions"
                + " required for this feature. It can be enable under App settings")
        .setPositiveButton("Settings"){ _, _ ->
          try{
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            ContextCompat.startActivity(context, intent, null)
          }catch (e: ActivityNotFoundException){
            e.printStackTrace()
          }
        }
        .setNegativeButton("Cancel"){
            dialog, _ ->
          dialog.dismiss()
        }.show()
    }
  }

}