package com.kotlin.sophosapp.viewModel

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
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
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.api.OfficeService
import com.kotlin.sophosapp.api.RestEngine
import com.kotlin.sophosapp.api.UserService
import com.kotlin.sophosapp.helpers.Constants
import com.kotlin.sophosapp.helpers.UserApp
import com.kotlin.sophosapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SendDocumentsViewModel: ViewModel() {


  val documentsType = listOf("Cedula de Ciudadania","Cedula de Extranjería", "Pasaporte")



  // LIVE DATA //
  val cameraAuth = MutableLiveData<CameraAuth?>()
  val galleryAuth = MutableLiveData<GalleryAuth?>()
  val mainCities = MutableLiveData<List<String>>()

  val citiesList = mutableSetOf<String>()


  //  PERMISSIONS //
  //  1.1 PERMISSIONS: CAMERA //
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
  // 1.2 PERMISSIONS: GALLERY  //
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
  //  RATIONAL DIALOG  //
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

  fun getOffice(){
    val officeService: OfficeService = RestEngine.getRestEngine().create(OfficeService::class.java)
    val call = officeService.fetchOffice()
    call.enqueue(object: Callback<RS_Cities>{
      override fun onResponse(call: Call<RS_Cities>, response: Response<RS_Cities>) {
        if(response.isSuccessful){
          val cities =  response.body()!!.Items

          for(city in cities){
            citiesList.add(city.Ciudad)
          }
          mainCities.postValue(citiesList.toList())

        }else {
          when (response.code()) {
            400 -> {
              Log.e("Error 400", "Bad Connection")
            }
            404 -> {
              Log.e("Error 404", "Not found")
            }
            else -> {
              Log.e("Error", "Generic Error")
            }
          }
        }
      }

      override fun onFailure(call: Call<RS_Cities>, t: Throwable) {
        Log.e("Error", t.message.toString())
        call.cancel()
      }
    })
  }



}