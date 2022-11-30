package com.kotlin.sophosapp.api

import com.kotlin.sophosapp.model.RS_Cities
import retrofit2.Call
import retrofit2.http.GET

interface OfficeService {
  @GET("RS_Oficinas")
  fun fetchOffice(): Call<RS_Cities>
}
