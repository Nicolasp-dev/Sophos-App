package com.kotlin.sophosapp.ui.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentDocumentsBinding
import com.kotlin.sophosapp.ui.viewModel.DocumentsViewModel
import com.kotlin.sophosapp.utils.*
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs
import com.kotlin.sophosapp.utils.adapter.DocumentsAdapter

class DocumentsFragment : Fragment() {

  private lateinit var viewModel: DocumentsViewModel
  private lateinit var _binding: FragmentDocumentsBinding
  private val binding get() = _binding

  private lateinit var recyclerView: RecyclerView
  private lateinit var documentAdapter: DocumentsAdapter
  private lateinit var communicator: Communicator
  private lateinit var title: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
    viewModel = ViewModelProvider(this)[DocumentsViewModel::class.java]
    communicator = activity as Communicator

    viewModel.getDocuments()

    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val language = prefs.getLanguage()
    title = if (language == "en") "Go back" else "Regresar"

    val toolbar = _binding.toolbarContainer.toolbar
    MyToolbar()
      .show(activity as AppCompatActivity, toolbar, title, true)

    recyclerView = _binding.docsRecyclerView

    viewModel.documents.observe(viewLifecycleOwner){
      documents -> run{
      _binding.loadingSchema.isVisible = false
      _binding.docsRecyclerView.isVisible = true

       recyclerView.apply {
          layoutManager = LinearLayoutManager(activity)
          documentAdapter =  DocumentsAdapter(documents!!){
            document -> viewModel.onDocumentSelected(document, communicator)
          }
          recyclerView.adapter = documentAdapter
         }
      }
    }
  }

  // ------------------------- [OPTION MENU SETTINGS] ------------------------- //
  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)

    val title = prefs.getThemeTitle()
    menu.findItem(R.id.op_theme).title = title

    val language = prefs.getLanguageTitle()
    menu.findItem(R.id.op_language).title = language
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return Routing.navigation(activity as AppCompatActivity, item, false)
  }
}

