package com.kotlin.sophosapp.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.data.model.rs_documents.Documents
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get

class DocumentsAdapter(private var documentsList: RS_Docs_Get, private val onClickListener: (Documents) -> Unit)
  : RecyclerView.Adapter<DocumentsViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.single_document, parent, false)
    return DocumentsViewHolder(view)
  }

  override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
    val document = documentsList.Items[position]
    holder.render(document, onClickListener)
  }

  override fun getItemCount(): Int = documentsList.Items.size
}

