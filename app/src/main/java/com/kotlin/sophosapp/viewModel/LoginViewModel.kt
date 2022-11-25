package com.kotlin.sophosapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.api.Constants
import com.kotlin.sophosapp.api.UserService
import com.kotlin.sophosapp.model.RS_User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel: ViewModel() {

  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  private val service: UserService = retrofit.create(UserService::class.java)

  val userData = MutableLiveData<RS_User?>()

  fun login(email: String, password: String){
    val call = service.fetchCredentials(email, password)
    call.enqueue(object : Callback<RS_User>{

      override fun onResponse(call: Call<RS_User>, response: Response<RS_User>) {
        if(response.isSuccessful){
          val responseBody = response.body()
          userData.postValue(responseBody)
          //Log.d("Success Response", responseBody.toString())
        }else {
          when (response.code()) {
            400 -> {
              Log.e("Error 400", "Bad Connection")
            }
            404 -> {
              Log.e("Error 404", "Not found")
            }
            else -> {
              Log.e("Error", "Generic Error")
            }
          }
        }
      }

      override fun onFailure(call: Call<RS_User>, t: Throwable) {
        Log.e("Error", t.message.toString())
        call.cancel()
      }

    })
  }
}