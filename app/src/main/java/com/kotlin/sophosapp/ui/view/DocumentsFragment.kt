package com.kotlin.sophosapp.ui.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentDocumentsBinding
import com.kotlin.sophosapp.utils.Communicator
import com.kotlin.sophosapp.utils.DocumentsAdapter
import com.kotlin.sophosapp.utils.MyToolbar
import com.kotlin.sophosapp.utils.Routing
import com.kotlin.sophosapp.ui.viewModel.DocumentsViewModel

class DocumentsFragment : Fragment() {

  private lateinit var viewModel: DocumentsViewModel
  private lateinit var _binding: FragmentDocumentsBinding
  private val binding get() = _binding

  private lateinit var recyclerView: RecyclerView
  private lateinit var documentAdapter: DocumentsAdapter
  private lateinit var communicator: Communicator



  // TODO -> SetDisplayOptions("ActionBar".displayShowCustom)
  // SetCustomView(R.layout.xmlFile)

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
    val toolbar = _binding.toolbarContainer.toolbar
    MyToolbar()
      .show(activity as AppCompatActivity, toolbar, "Regresar", true)
    recyclerView = _binding.docsRecyclerView

    viewModel.documents.observe(viewLifecycleOwner){
      documents -> run{
         recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            documentAdapter =  DocumentsAdapter(documents!!)
            recyclerView.adapter = documentAdapter
            documentAdapter.onItemClick = {
              documents -> run{
                communicator.passData(documents.IdRegistro)

              }
            }
         }
      }
    }
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
    return Routing().navigation(activity as AppCompatActivity, item, false)
  }
}

