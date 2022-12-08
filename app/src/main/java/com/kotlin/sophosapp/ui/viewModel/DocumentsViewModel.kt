package com.kotlin.sophosapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.data.network.service.DocumentsService
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocumentsViewModel : ViewModel() {

  val documents = MutableLiveData<RS_Docs_Get?>()
  val isLoading = MutableLiveData<Boolean>()

  fun getDocuments(){
    isLoading.postValue(true)
    val email = prefs.getUserEmail()

    DocumentsService().fetchDocumentsByEmail(email)
      .enqueue(object: Callback<RS_Docs_Get>{

      override fun onResponse(call: Call<RS_Docs_Get>, response: Response<RS_Docs_Get>) {
        if(response.isSuccessful){
          isLoading.postValue(false)
          val responseBody = response.body()
          documents.postValue(responseBody)
        }else {
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Docs_Get>, t: Throwable) {
        Log.e("Error", t.message.toString())
        call.cancel()
      }

    })
  }
}