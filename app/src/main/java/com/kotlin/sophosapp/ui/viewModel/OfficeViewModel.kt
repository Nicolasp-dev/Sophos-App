package com.kotlin.sophosapp.ui.viewModel

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
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
import com.kotlin.sophosapp.utils.Dialog
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfficeViewModel : ViewModel() {

  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var lastLocation: Location

  fun getOfficesLocations(map: GoogleMap) {

    OfficeService().fetchOfficesData().enqueue(object: Callback<RS_Cities>{

      override fun onResponse(call: Call<RS_Cities>, response: Response<RS_Cities>) {
        if(response.isSuccessful){
          val locations = response.body()
          for (location in locations!!.Items){
            if(location.cityName == "Chile"){
              createMarkers(map, "-${location.latitude}".toDouble(), location.longitude.toDouble(), location.placeName)
            }else{
              createMarkers(map,location.latitude.toDouble(), location.longitude.toDouble(), location.placeName)
            }

          }
        }else{
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Cities>, t: Throwable) {
        call.cancel()
      }

    })

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
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            currentLocation(map, fusedLocationClient)
          }
        }
      }

      override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
      ) {
        Dialog.showAlertDialogPermission(context)
      }

    }).onSameThread().check()
    return locationEnable
  }

  @SuppressLint("MissingPermission")
  private fun currentLocation(map: GoogleMap, fusedLocation: FusedLocationProviderClient){
    map.uiSettings.isZoomControlsEnabled = true
    fusedLocation.lastLocation.addOnSuccessListener{
      location -> lastLocation = location
      val currentLatLng = LatLng(location.latitude, location.longitude)
      map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,12f), 5000, null)
    }
  }
}