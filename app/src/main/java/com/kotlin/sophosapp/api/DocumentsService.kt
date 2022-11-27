package com.kotlin.sophosapp.api

import com.kotlin.sophosapp.model.RS_Docs_Submmit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DocumentsService {
  @POST("RS_Documentos")
  fun sendDocument(@Body post: RS_Docs_Submmit): Call<RS_Docs_Submmit>
}