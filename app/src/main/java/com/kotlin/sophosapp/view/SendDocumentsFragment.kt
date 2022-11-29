package com.kotlin.sophosapp.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentSendDocumentsBinding
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.Routing
import com.kotlin.sophosapp.viewModel.SendDocumentsViewModel

class SendDocumentsFragment : Fragment() {

  private lateinit var viewModel: SendDocumentsViewModel
  private lateinit var _binding: FragmentSendDocumentsBinding
  private val binding get() = _binding

  // ------------------------- [ON CREATE VIEW] ------------------------- //
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    viewModel = ViewModelProvider(this)[SendDocumentsViewModel::class.java]
    _binding = FragmentSendDocumentsBinding.inflate(inflater, container, false )

    // --------------- [DOCUMENTS DROPDOWN] ---------------------- //
    val items = listOf("Document1","Document2","Document3","Document4")
    val adapter =  ArrayAdapter(activity as AppCompatActivity, R.layout.list_documents, items)
    binding.dropdownMenuDocument.setAdapter(adapter)

    val cities = listOf("Bogota","Medellin","Seattle","Ontario")
    val cityAdapter =  ArrayAdapter(activity as AppCompatActivity, R.layout.list_documents, cities)
    binding.dropdownMenuCities.setAdapter(cityAdapter)
    // -------------------------------------------------------------- //

    setClickListener(activity as AppCompatActivity)

    return binding.root
  }


  // ------------------------- [ON CREATE] ------------------------- //
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  // ------------------------- [ON VIEW CREATED] ------------------------- //
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    MyToolbar()
      .show(activity as AppCompatActivity,binding.toolbarContainer.toolbar,"Regresar", true)
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

  private fun setClickListener(context: AppCompatActivity){
    binding.ivAddImage.setOnClickListener{viewModel.cameraCheckPermission(context)}
    binding.btnAddDocument.setOnClickListener{viewModel.galleryCheckPermission(context)}
  }
}