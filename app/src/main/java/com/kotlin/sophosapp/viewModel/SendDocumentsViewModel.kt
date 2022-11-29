package com.kotlin.sophosapp.viewModel

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.kotlin.sophosapp.helpers.Constants
import com.kotlin.sophosapp.model.CameraAuth
import com.kotlin.sophosapp.model.GalleryAuth
import com.kotlin.sophosapp.model.isAuth

class SendDocumentsViewModel: ViewModel() {

  // ====================== [LIVE DATA] ====================== //
  val cameraAuth = MutableLiveData<CameraAuth?>()
  val galleryAuth = MutableLiveData<GalleryAuth?>()
  // ========================================================= //

  // [ 1. PERMISSIONS ] -------------------------------------------------------//
  // [ 1.1 PERMISSIONS: CAMERA ] --------------------------------------------- //
  fun cameraCheckPermission(context: AppCompatActivity){
    Dexter.withContext(context)
      .withPermissions(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
      )
      .withListener( object: MultiplePermissionsListener{
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
          report?.let{

            if(report.areAllPermissionsGranted()){
              cameraAuth.postValue(CameraAuth(isAuth = true))
            }
          }
        }

        override fun onPermissionRationaleShouldBeShown(
          p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
          p1: PermissionToken?
        ) {
          showRotationalDialogPermission(context)
        }
      }).onSameThread().check()
  }
  // [ 1.2 PERMISSIONS: GALLERY ] --------------------------------------------------- //
  fun galleryCheckPermission(context: AppCompatActivity){
    Dexter.withContext(context)
      .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
      .withListener(object: PermissionListener{

        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
          galleryAuth.postValue(GalleryAuth(isAuth = true))
        }

        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
          Toast.makeText(
            context,
            "You have denied the storage permission to select image",
            Toast.LENGTH_SHORT
          )
            .show()
          showRotationalDialogPermission(context)
        }

        override fun onPermissionRationaleShouldBeShown(
          p0: com.karumi.dexter.listener.PermissionRequest?,
          p1: PermissionToken?
        ) {
          showRotationalDialogPermission(context)
        }
      }).onSameThread().check()
  }

  // ====================== [ RATIONAL DIALOG ] ====================== //
  private fun showRotationalDialogPermission(context: AppCompatActivity){
    AlertDialog.Builder(context)
      .setMessage( "It looks like you have turned off permissions"
              + "requires for this feature. It can enable under App settings")
      .setPositiveButton("Go to Settings"){ _, _ ->
        try{
          val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
          val uri = Uri.fromParts("package", context.packageName, null)
          intent.data = uri
          startActivity(context, intent, null)
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