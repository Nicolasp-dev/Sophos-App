package com.kotlin.sophosapp.data.network.api

import com.kotlin.sophosapp.data.model.rs_user.RS_User
import com.kotlin.sophosapp.utils.Constants.ID_REGISTER
import com.kotlin.sophosapp.utils.Constants.RS_USERS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiClient {
  @GET(RS_USERS)
  fun fetchCredentials(
    @Query("idUsuario") userEmail: String,
    @Query("clave") userPassword: String
  ): Call<RS_User>
}