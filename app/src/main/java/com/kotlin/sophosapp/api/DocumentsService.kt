package com.kotlin.sophosapp.api

import com.kotlin.sophosapp.model.RS_Docs_Get
import com.kotlin.sophosapp.model.Documents
import com.kotlin.sophosapp.model.RS_Docs_Submmit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DocumentsService {
  @GET("RS_Documentos")
  fun fetchDocuments(
    @Query("correo") email: String
  ): Call<RS_Docs_Get>


  @GET("RS_Documentos")
  fun fetchDocumentById(
    @Query("idRegistro") Id: String
  ): Call<RS_Docs_Get>


  @POST("RS_Documentos")
  fun sendDocument(@Body userData: RS_Docs_Submmit): Call<RS_Docs_Submmit>
}