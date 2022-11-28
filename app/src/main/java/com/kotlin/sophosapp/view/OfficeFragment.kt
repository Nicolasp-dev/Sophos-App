package com.kotlin.sophosapp.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentOfficeBinding
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.Routing
import com.kotlin.sophosapp.viewModel.OfficeViewModel

class OfficeFragment : Fragment() {

  private lateinit var _binding : FragmentOfficeBinding
  private val binding get() = _binding
  private lateinit var viewModel: OfficeViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentOfficeBinding.inflate(inflater, container, false)
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

}