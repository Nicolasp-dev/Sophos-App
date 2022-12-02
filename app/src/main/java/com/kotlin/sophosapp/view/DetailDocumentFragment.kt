package com.kotlin.sophosapp.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentDetailDocumentBinding
import com.kotlin.sophosapp.databinding.FragmentDocumentsBinding
import com.kotlin.sophosapp.helpers.Constants
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.Routing
import com.kotlin.sophosapp.helpers.UserApp
import com.kotlin.sophosapp.viewModel.DetailDocumentViewModel

class DetailDocumentFragment : Fragment() {

  private lateinit var _binding: FragmentDetailDocumentBinding
  private val binding get() = _binding
  private lateinit var viewModel: DetailDocumentViewModel
  private lateinit var idRegister: String


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDetailDocumentBinding.inflate(inflater, container, false)
    viewModel = ViewModelProvider(this)[DetailDocumentViewModel::class.java]
    idRegister = requireArguments().getString(Constants.ID_REGISTER,"")
    Log.i("ID REGISTER", idRegister)

    viewModel.getDetails(idRegister)
    viewModel.decodedImage.observe(viewLifecycleOwner){
      image -> _binding.detailImage.setImageBitmap(image)
    }
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

  // ------------------------- [OPTION MENU SETTINGS] ------------------------- //
  // Inflate the menu.
  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return Routing().navigation(activity as AppCompatActivity, item, true)
  }

}