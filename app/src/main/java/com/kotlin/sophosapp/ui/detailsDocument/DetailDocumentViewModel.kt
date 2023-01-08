package com.kotlin.sophosapp.ui.documentDetail

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.data.network.service.DocumentsService
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get
import com.kotlin.sophosapp.utils.ImageTools
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDocumentViewModel : ViewModel() {

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading : LiveData<Boolean> = _isLoading

  private val _decodedImage = MutableLiveData<Bitmap?>()
  val decodedImage: LiveData<Bitmap?> = _decodedImage

  fun getDetails(id: String){
    _isLoading.postValue(true)
    DocumentsService().fetchDocumentById(id).enqueue(object: Callback<RS_Docs_Get> {

      override fun onResponse(call: Call<RS_Docs_Get>, response: Response<RS_Docs_Get>) {
        if(response.isSuccessful){
          _isLoading.postValue(false)
          val responseBody = response.body()
          responseBody!!.Items.forEach { item ->
            val image = ImageTools.decodeImage(item.file)
            _decodedImage.postValue(image)
          }
        }else{
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Docs_Get>, t: Throwable) {
        call.cancel()
      }
    })
  }
}