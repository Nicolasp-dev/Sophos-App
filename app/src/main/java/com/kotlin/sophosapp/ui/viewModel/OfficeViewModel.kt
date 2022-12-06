package com.kotlin.sophosapp.ui.viewModel

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kotlin.sophosapp.data.network.service.OfficeService
import com.kotlin.sophosapp.data.model.rs_offices.RS_Cities
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfficeViewModel : ViewModel() {

  fun getOfficesLocations(map: GoogleMap) {

    OfficeService().fetchOfficesData().enqueue(object: Callback<RS_Cities>{

      override fun onResponse(call: Call<RS_Cities>, response: Response<RS_Cities>) {
        if(response.isSuccessful){
          val locations = response.body()
          for (location in locations!!.Items){
            createMarkers(map,location.latitude.toDouble(), location.longitude.toDouble(), location.placeName)
          }
        }else{
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Cities>, t: Throwable) {
        call.cancel()
      }

    })

    //val coordinates = LatLng(latitude, longitude)
    //map.addMarker(MarkerOptions().position(coordinates).title(title))

    /*


    map.animateCamera(
      CameraUpdateFactory.newLatLngZoom(coordinates, 5f),
      5000,
       null
    )

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 10F))
     */
  }

  fun createMarkers(map: GoogleMap, latitude: Double, longitude: Double, title: String){
    val city = LatLng(latitude, longitude)
    map.addMarker(MarkerOptions().position(city).title(title))
  }

  fun isLocationPermissionGranted(context: AppCompatActivity, map: GoogleMap): Boolean{
    var locationEnable = false

    Dexter.withContext(context).withPermissions(
      android.Manifest.permission.ACCESS_FINE_LOCATION,
      android.Manifest.permission.ACCESS_COARSE_LOCATION
    ).withListener( object: MultiplePermissionsListener {
      @SuppressLint("MissingPermission")
      override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        report?.let{
          if(report.areAllPermissionsGranted()){
            map.isMyLocationEnabled = true
            locationEnable = true
          }
        }
      }

      override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
      ) {
        showRotationalDialogPermission(context)
      }

    }).onSameThread().check()
    return locationEnable
  }

  private fun showRotationalDialogPermission(context: AppCompatActivity){
    AlertDialog.Builder(context)
      .setMessage( "It looks like you have turned off permissions"
              + "requires for this feature. It can enable under App settings")
      .setPositiveButton("Go to Settings"){ _, _ ->
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