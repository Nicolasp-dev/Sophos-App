package com.kotlin.sophosapp.data.network.api

import com.kotlin.sophosapp.data.model.rs_offices.RS_Cities
import com.kotlin.sophosapp.utils.Constants.RS_OFFICES
import retrofit2.Call
import retrofit2.http.GET

interface OfficeApiClient {
  @GET(RS_OFFICES)
  fun fetchData(): Call<RS_Cities>
}
