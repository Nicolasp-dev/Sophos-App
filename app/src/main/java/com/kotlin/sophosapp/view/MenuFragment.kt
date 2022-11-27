package com.kotlin.sophosapp.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentMenuBinding
import com.kotlin.sophosapp.helpers.MyToolbar
import com.kotlin.sophosapp.helpers.UserApp
import com.kotlin.sophosapp.helpers.UserApp.Companion.prefs
import com.kotlin.sophosapp.helpers.UserApp.Companion.routing

class MenuFragment : Fragment() {

  private lateinit var _binding: FragmentMenuBinding
  private val binding get() = _binding

  // ------------------------- [ON CREATE VIEW] ------------------------- //
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMenuBinding.inflate(inflater, container, false)
    return binding.root
  }

  // ------------------------- [ON CREATE] ------------------------- //
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Enable options Menu in this Fragment.
    setHasOptionsMenu(true)
  }

  // ------------------------- [ON VIEW CREATED] ------------------------- //
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val userName = prefs.getUsername()
    MyToolbar().show(activity as AppCompatActivity, binding.toolbarContainer.toolbar ,userName, false)
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
    return routing.navigation(activity as AppCompatActivity, item)
  }
}