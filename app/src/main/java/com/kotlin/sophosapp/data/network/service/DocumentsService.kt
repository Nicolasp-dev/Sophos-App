package com.kotlin.sophosapp.data.network.service

import com.kotlin.sophosapp.core.RetroEngine
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Submmit
import com.kotlin.sophosapp.data.network.api.DocumentsApiClient
import retrofit2.Call

class DocumentsService {
  private val service = RetroEngine.getRestEngine().create(DocumentsApiClient::class.java)

  fun fetchDocumentsByEmail(email: String): Call<RS_Docs_Get> {
      return service.fetchDocumentsByEmail(email)
  }

  fun fetchDocumentById(id: String): Call<RS_Docs_Get> {
      return service.fetchDocumentById(id)
  }

  fun submitDocuments(data: RS_Docs_Submmit): Call<RS_Docs_Submmit> {
    return service.sendDocument(data)
  }
}