package com.kotlin.sophosapp.api

import com.kotlin.sophosapp.model.RS_User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
  @GET("RS_Usuarios")
  fun fetchCredentials(
    @Query("idUsuario") userEmail: String,
    @Query("clave") userPassword: String
  ): Call<RS_User>
}