package com.kotlin.sophosapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.SingleDocumentBinding
import com.kotlin.sophosapp.data.model.rs_documents.Documents
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get

class DocumentsAdapter(private var documentsList: RS_Docs_Get)
  : RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder>() {

  var onItemClick: ((Documents) -> Unit)? = null

  inner class DocumentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var binding = SingleDocumentBinding.bind(itemView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.single_document, parent, false)
    return DocumentsViewHolder(view)
  }

  override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
    val document = documentsList.Items[position]
    holder.binding.tvName.text = document.Nombre
    holder.binding.tvDate.text = document.Fecha
    holder.binding.tvDescription.text = document.TipoAdjunto

    holder.itemView.setOnClickListener {
      onItemClick?.invoke(document)
      document.IdRegistro
    }
  }

  override fun getItemCount(): Int {
    return documentsList.Items.size
  }
}

