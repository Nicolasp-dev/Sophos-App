package com.kotlin.sophosapp.ui.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.data.network.service.DocumentsService
import com.kotlin.sophosapp.data.model.rs_documents.RS_Docs_Get
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDocumentViewModel : ViewModel() {

  val isLoading = MutableLiveData<Boolean>()
  val decodedImage = MutableLiveData<Bitmap?>()

  fun getDetails(id: String){
    isLoading.postValue(true)
    DocumentsService().fetchDocumentById(id).enqueue(object: Callback<RS_Docs_Get> {

      override fun onResponse(call: Call<RS_Docs_Get>, response: Response<RS_Docs_Get>) {
        if(response.isSuccessful){
          val responseBody = response.body()
          responseBody!!.Items.forEach { item ->
            val image = decodeRespImage(item.Adjunto)
            decodedImage.postValue(image)
          }
          isLoading.postValue(false)
        }else{
          NonSuccessResponse().message(response.code())
        }
      }

      override fun onFailure(call: Call<RS_Docs_Get>, t: Throwable) {
        call.cancel()
      }
    })

  }

  private fun decodeRespImage(image: String): Bitmap? {
    val byteArray: ByteArray = Base64.decode(image, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
  }
}