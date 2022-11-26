package com.kotlin.sophosapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.viewModel.DocumentsViewModel

class DocumentsFragment : Fragment() {

  companion object {
    fun newInstance() = DocumentsFragment()
  }

  private lateinit var viewModel: DocumentsViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_documents, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(DocumentsViewModel::class.java)
    // TODO: Use the ViewModel
  }

}