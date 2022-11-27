package com.kotlin.sophosapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentDocumentsBinding
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.UserApp
import com.kotlin.sophosapp.helpers.UserApp.Companion.routing
import com.kotlin.sophosapp.viewModel.DocumentsViewModel

class DocumentsFragment : Fragment() {

  private lateinit var viewModel: DocumentsViewModel
  private lateinit var _binding: FragmentDocumentsBinding
  private val binding get() = _binding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDocumentsBinding.inflate(inflater, container, false)

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
  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return routing.navigation(activity as AppCompatActivity, item)
  }

}

