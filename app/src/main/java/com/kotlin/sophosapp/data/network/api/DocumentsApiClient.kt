package com.kotlin.sophosapp.data.network.api

import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Submmit
import com.kotlin.sophosapp.utils.Constants.RS_DOCS
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DocumentsApiClient {
  @GET(RS_DOCS)
  fun fetchDocumentsByEmail(
    @Query("correo") email: String
  ): Call<RS_Docs_Get>


  @GET(RS_DOCS)
  fun fetchDocumentById(
    @Query("idRegistro") Id: String
  ): Call<RS_Docs_Get>


  @POST(RS_DOCS)
  fun sendDocument(@Body userData: RS_Docs_Submmit): Call<RS_Docs_Submmit>
}