package com.kotlin.sophosapp.view

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentSendDocumentsBinding
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.Routing

class SendDocumentsFragment : Fragment() {

  private lateinit var _binding: FragmentSendDocumentsBinding
  private val binding get() = _binding

  // ------------------------- [ON CREATE VIEW] ------------------------- //
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentSendDocumentsBinding.inflate(inflater, container, false )

    // --------------- [Documents dropdown] ---------------------- //
    val items = listOf("Document1","Document2","Document3","Document4")
    val adapter =  ArrayAdapter(activity as AppCompatActivity, R.layout.list_documents, items)
    binding.dropdownMenuDocument.setAdapter(adapter)

    val cities = listOf("Bogota","Medellin","Seattle","Ontario")
    val cityAdapter =  ArrayAdapter(activity as AppCompatActivity, R.layout.list_documents, cities)
    binding.dropdownMenuCities.setAdapter(cityAdapter)

    return binding.root
  }


  // ------------------------- [ON CREATE] ------------------------- //
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }
  // --------------------------------------------------------------- //

  // ------------------------- [ON VIEW CREATED] ------------------------- //
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    MyToolbar().show(
      activity as AppCompatActivity,
      binding.toolbarContainer.toolbar,
      "Regresar", true)
  }


  // ------------------------- [OPTION MENU SETTINGS] ------------------------- //
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



}