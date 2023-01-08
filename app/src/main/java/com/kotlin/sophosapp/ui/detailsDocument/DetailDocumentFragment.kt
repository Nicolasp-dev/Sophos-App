package com.kotlin.sophosapp.ui.documentDetail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentDetailDocumentBinding
import com.kotlin.sophosapp.utils.Constants
import com.kotlin.sophosapp.utils.MyToolbar
import com.kotlin.sophosapp.utils.Routing
import com.kotlin.sophosapp.utils.UserApp

class DetailDocumentFragment : Fragment() {

  private val viewModel: DetailDocumentViewModel by viewModels()

  private lateinit var _binding: FragmentDetailDocumentBinding
  private val binding get() = _binding

  private lateinit var idRegister: String
  private lateinit var title: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
      _binding = FragmentDetailDocumentBinding.inflate(inflater, container, false)
      idRegister = requireArguments().getString(Constants.ID_REGISTER,"")

    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val toolbar = _binding.toolbarContainer.toolbar
    val language = UserApp.prefs.getLanguage()
    title = if (language == "en") "Go back" else "Regresar"

    MyToolbar()
      .show(activity as AppCompatActivity, toolbar, title, true)

    viewModel.decodedImage.observe(viewLifecycleOwner){
        image -> _binding.detailImage.setImageBitmap(image)
    }

    viewModel.isLoading.observe(viewLifecycleOwner){
        currentState -> _binding.progressBar.isVisible = currentState
    }

    viewModel.getDetails(idRegister)
  }

  // ------------------------- [OPTION MENU SETTINGS] ------------------------- //
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
    return Routing.navigation(activity as AppCompatActivity, item, true)
  }
}















