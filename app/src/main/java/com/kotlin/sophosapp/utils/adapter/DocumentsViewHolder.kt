package com.kotlin.sophosapp.utils.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.sophosapp.data.model.rs_documents.Documents
import com.kotlin.sophosapp.databinding.SingleDocumentBinding

class DocumentsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var binding = SingleDocumentBinding.bind(itemView)

    fun render(document: Documents, onClickListener: (Documents) -> Unit){
      binding.tvName.text = document.name
      binding.tvDate.text = document.date.take(10)
      binding.tvDescription.text = document.fileType.take(10)

      itemView.setOnClickListener {
        onClickListener(document)
      }
    }
}
