package com.kotlin.sophosapp.data.network.service

import com.kotlin.sophosapp.core.RetroEngine
import com.kotlin.sophosapp.data.model.rs_offices.RS_Cities
import com.kotlin.sophosapp.data.network.api.OfficeApiClient
import retrofit2.Call

class OfficeService {
  fun fetchOfficesData(): Call<RS_Cities>{
    return RetroEngine.getRestEngine().create(OfficeApiClient::class.java).fetchData()
  }
}