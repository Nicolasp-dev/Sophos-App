package com.kotlin.sophosapp.ui.viewModel

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.kotlin.sophosapp.data.network.service.DocumentsService
import com.kotlin.sophosapp.data.model.auth.CameraAuth
import com.kotlin.sophosapp.data.model.auth.GalleryAuth
import com.kotlin.sophosapp.data.model.rs_offices.RS_Cities
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Submmit
import com.kotlin.sophosapp.data.network.service.OfficeService
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


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
              + " requires for this feature. It can enable under App settings")
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
    OfficeService().fetchOfficesData().enqueue(object: Callback<RS_Cities>{
      override fun onResponse(call: Call<RS_Cities>, response: Response<RS_Cities>) {
        if(response.isSuccessful){
          val cities =  response.body()!!.Items
          cities.forEach { city -> citiesList.add(city.cityName) }
          mainCities.postValue(citiesList.toList())
        }else {
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Cities>, t: Throwable) {
        Log.e("Error", t.message.toString())
        call.cancel()
      }
    })
  }

  fun encodeImage(bm: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
  }

  fun submitData(image: String, description: String, docType: String, docId: String, name: String,
                 lastname: String, email: String, city: String, context: AppCompatActivity ){

    val data = RS_Docs_Submmit(docType,docId,name,lastname,city,email, description,image)

    DocumentsService().submitDocuments(data)
      .enqueue(object: Callback<RS_Docs_Submmit>{

      override fun onResponse(call: Call<RS_Docs_Submmit>, response: Response<RS_Docs_Submmit>) {
        if(response.isSuccessful){
           Toast.makeText(context, "DOCUMENT SUBMITTED", Toast.LENGTH_SHORT).show()
        }else {
          Toast.makeText(context, "THE IMAGE IS TO HEAVY TO UPLOAD", Toast.LENGTH_SHORT).show()
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Docs_Submmit>, t: Throwable) {
        Log.e("Error", t.message.toString())
        call.cancel()
      }
    })
  }

}