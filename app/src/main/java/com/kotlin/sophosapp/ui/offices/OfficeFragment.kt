package com.kotlin.sophosapp.ui.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentOfficeBinding
import com.kotlin.sophosapp.utils.MyToolbar
import com.kotlin.sophosapp.utils.Routing
import com.kotlin.sophosapp.ui.viewModel.OfficeViewModel
import com.kotlin.sophosapp.utils.UserApp

class OfficeFragment : Fragment(), OnMapReadyCallback {

  private lateinit var _binding : FragmentOfficeBinding
  private val binding get() = _binding
  private lateinit var viewModel: OfficeViewModel
  private lateinit var map: GoogleMap
  private lateinit var title: String

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
    val toolbar = binding.toolbarContainer.toolbar
    val language = UserApp.prefs.getLanguage()
    title = if (language == "en") "Go back" else "Regresar"
    MyToolbar().show(activity as AppCompatActivity, toolbar, title, true)
  }

  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)

    val title = UserApp.prefs.getThemeTitle()
    menu.findItem(R.id.op_theme).title = title

    val language = UserApp.prefs.getLanguageTitle()
    menu.findItem(R.id.op_language).title = language
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return Routing.navigation(activity as AppCompatActivity, item)
  }


  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    viewModel.isLocationPermissionGranted(activity as AppCompatActivity, map)
    viewModel.getOfficesLocations(map)

  }

}


