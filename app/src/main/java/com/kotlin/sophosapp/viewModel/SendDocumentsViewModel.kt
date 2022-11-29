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
import androidx.lifecycle.ViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.kotlin.sophosapp.helpers.Constants

class SendDocumentsViewModel: ViewModel() {

  // -------------------------- [::: PERMISSIONS :::] ----------------------- //

  // ------------------------- [ PERMISSIONS: CAMERA ] ----------------------- //
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
              takeImage(context)
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

  // ------------------------- [ PERMISSIONS: CAMERA ] ----------------------- //
  fun galleryCheckPermission(context: AppCompatActivity){
    Dexter.withContext(context)
      .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
      .withListener(object: PermissionListener{

        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
          selectImageFromGallery(context)
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


  // ------------------------ [::: ACTIONS :::] ----------------------- //

  // ------------------------ [ CAMERA ACTION ] ----------------------- //

  private fun takeImage(context: AppCompatActivity){
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(context, intent, Constants.CAMERA_REQUEST_CODE, null)
  }

  // ------------------------ [ GALLERY ACTION ] ---------------------- //

  private fun selectImageFromGallery(context: AppCompatActivity){
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    startActivityForResult(context, intent, Constants.GALLERY_REQUEST_CODE, null)
  }




  // ----------------------- [ RATIONAL DIALOG ] --------------------------- //
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