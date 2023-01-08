package com.kotlin.sophosapp.ui.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentMenuBinding
import com.kotlin.sophosapp.utils.MyToolbar
import com.kotlin.sophosapp.utils.Routing
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs


class MenuFragment : Fragment() {

  private lateinit var _binding: FragmentMenuBinding
  private val binding get() = _binding

  // ------------------------- [ON CREATE VIEW] ------------------------- //
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMenuBinding.inflate(inflater, container, false)

    // TODO -> This should be at ViewModel ?
    _binding.btnSendDocuments.setOnClickListener{
      Routing.goTo(activity as AppCompatActivity, SendDocumentsFragment() )
    }

    _binding.btnSeeDocuments.setOnClickListener {
      Routing.goTo(activity as AppCompatActivity, DocumentsFragment())
    }

    _binding.btnOffice.setOnClickListener {
      Routing.goTo(activity as AppCompatActivity, OfficeFragment())
    }

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
    val toolbar = binding.toolbarContainer.toolbar
    val userName = prefs.getUsername()
    MyToolbar().show(activity as AppCompatActivity, toolbar, userName, false)
  }

  // ------------------------- [OPTION MENU SETTINGS] ------------------------- //
  // Inflate the menu.
  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)

    val theme = prefs.getThemeTitle()
    menu.findItem(R.id.op_theme).title = theme

    val language = prefs.getLanguageTitle()
    menu.findItem(R.id.op_language).title = language
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return Routing.navigation(activity as AppCompatActivity, item)
  }
}