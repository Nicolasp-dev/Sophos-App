package com.kotlin.sophosapp.ui.viewModel

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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
import com.kotlin.sophosapp.data.network.service.DocumentsService
import com.kotlin.sophosapp.data.model.auth.CameraAuth
import com.kotlin.sophosapp.data.model.auth.GalleryAuth
import com.kotlin.sophosapp.data.model.image.ImageData
import com.kotlin.sophosapp.data.model.rs_offices.RS_Cities
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Submmit
import com.kotlin.sophosapp.data.network.service.OfficeService
import com.kotlin.sophosapp.utils.Constants
import com.kotlin.sophosapp.utils.Dialog
import com.kotlin.sophosapp.utils.ImageTools
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


class SendDocumentsViewModel: ViewModel() {

  val documentsType = listOf("Cedula de Ciudadania","Cedula de Extranjería", "Pasaporte")

  //private lateinit var encodedImage: String

  // LIVE DATA //
  private val _cameraAuth = MutableLiveData<CameraAuth?>()
  val cameraAuth: LiveData<CameraAuth?> = _cameraAuth

  private val _galleryAuth = MutableLiveData<GalleryAuth?>()
  val galleryAuth: LiveData<GalleryAuth?> = _galleryAuth

  private val _mainCities = MutableLiveData<List<String>>()
  val mainCities: LiveData<List<String>> = _mainCities

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
            if(report.isAnyPermissionPermanentlyDenied){
              Toast.makeText(context, "No permissions Allowed", Toast.LENGTH_SHORT).show()
            }
            if(report.areAllPermissionsGranted()){
              _cameraAuth.postValue(CameraAuth(isAuth = true))
            }
          }
        }

        override fun onPermissionRationaleShouldBeShown(
          p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
          p1: PermissionToken?
        ) {
          Dialog.showAlertDialogPermission(context)
        }
      }).onSameThread().check()
  }
  // 1.2 PERMISSIONS: GALLERY  //
  fun galleryCheckPermission(context: AppCompatActivity){
    Dexter.withContext(context)
      .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
      .withListener(object: PermissionListener{

        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
          _galleryAuth.postValue(GalleryAuth(isAuth = true))
        }

        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
          Toast.makeText(
            context,
            "You have denied the storage permission to select image",
            Toast.LENGTH_SHORT
          )
            .show()
          Dialog.showAlertDialogPermission(context)
        }

        override fun onPermissionRationaleShouldBeShown(
          p0: com.karumi.dexter.listener.PermissionRequest?,
          p1: PermissionToken?
        ) {
          Dialog.showAlertDialogPermission(context)
        }
      }).onSameThread().check()
  }

  fun getOffice(){
    OfficeService().fetchOfficesData().enqueue(object: Callback<RS_Cities>{
      override fun onResponse(call: Call<RS_Cities>, response: Response<RS_Cities>) {
        if(response.isSuccessful){
          val cities =  response.body()!!.Items
          cities.forEach { city -> citiesList.add(city.cityName) }
          _mainCities.postValue(citiesList.toList())
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

  fun imageAction(requestCode: Int, data: Intent?, mActivity: FragmentActivity?): Array<ImageData> {

     when(requestCode){
      Constants.CAMERA_REQUEST_CODE -> {
        val bitmap = data?.extras?.get("data") as Bitmap
        val encodedImage = ImageTools.encodeImage(bitmap)

        return arrayOf(ImageData(bitmap, encodedImage))
      }

        Constants.GALLERY_REQUEST_CODE -> {
          val image = data?.data
          val bitmap = MediaStore.Images.Media.getBitmap(mActivity?.contentResolver, image)
          val encodedImage = ImageTools.encodeImage(bitmap)

          return arrayOf(ImageData(bitmap, encodedImage))
        }
        else ->  return arrayOf(ImageData( null, null))
      }

  }

  fun submitData(image: String, description: String, docType: String, docId: String, name: String,
                 lastname: String, email: String, city: String, context: AppCompatActivity ) {

    val data = RS_Docs_Submmit(docType, docId, name, lastname, city, email, description, image)

    DocumentsService().submitDocuments(data)
      .enqueue(object : Callback<RS_Docs_Submmit> {

        override fun onResponse(call: Call<RS_Docs_Submmit>, response: Response<RS_Docs_Submmit>) {
          if (response.isSuccessful) {
            Toast.makeText(context, "DOCUMENT SUBMITTED", Toast.LENGTH_SHORT).show()
          } else {
            Toast.makeText(context, "THE IMAGE IS TO HEAVY TO UPLOAD", Toast.LENGTH_SHORT).show()
            NonSuccessResponse().message(response.code())
          }
        }

        override fun onFailure(call: Call<RS_Docs_Submmit>, t: Throwable) {
          call.cancel()
        }
      })
    }
}

