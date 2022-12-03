package com.kotlin.sophosapp.data.network.service

import com.kotlin.sophosapp.core.RetroEngine
import com.kotlin.sophosapp.data.model.rs_user.RS_User
import com.kotlin.sophosapp.data.network.api.UserApiClient
import retrofit2.Call

class UserService {
  fun fetchUserData(email: String, password: String): Call<RS_User> {
    return RetroEngine.getRestEngine().create(UserApiClient::class.java).fetchCredentials(email, password)
  }
}