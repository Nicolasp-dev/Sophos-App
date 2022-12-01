package com.kotlin.sophosapp.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentOfficeBinding
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.Routing
import com.kotlin.sophosapp.viewModel.OfficeViewModel

class OfficeFragment : Fragment(), OnMapReadyCallback {

  private lateinit var _binding : FragmentOfficeBinding
  private val binding get() = _binding
  private lateinit var viewModel: OfficeViewModel
  private lateinit var map: GoogleMap

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentOfficeBinding.inflate(inflater, container, false)
    viewModel = ViewModelProvider(this)[OfficeViewModel::class.java]

    val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
    mapFragment.getMapAsync(this)

    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    MyToolbar().show(activity as AppCompatActivity, binding.toolbarContainer.toolbar, "Regresar", true)
  }

  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return Routing().navigation(activity as AppCompatActivity, item)
  }


  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    createMarker()
  }

  private fun createMarker() {
    val coordinates = LatLng(6.152676, -75.584246)
    val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Home")
    map.addMarker(marker)
  }
}


