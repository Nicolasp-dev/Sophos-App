package com.kotlin.sophosapp.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.api.DocumentsService
import com.kotlin.sophosapp.api.RestEngine
import com.kotlin.sophosapp.model.RS_Docs_Get
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDocumentViewModel : ViewModel() {

  private val docsService: DocumentsService = RestEngine.getRestEngine().create(DocumentsService::class.java)

  val decodedImage = MutableLiveData<Bitmap?>()


  fun getDetails(id: String){
    docsService.fetchDocumentById(id).enqueue(object: Callback<RS_Docs_Get> {
      override fun onResponse(call: Call<RS_Docs_Get>, response: Response<RS_Docs_Get>) {
        if(response.isSuccessful){
          val responseBody = response.body()
          responseBody!!.Items.forEach { item ->
            val image = decodeRespImage(item.Adjunto)
            decodedImage.postValue(image)
          }
        }else{
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

      override fun onFailure(call: Call<RS_Docs_Get>, t: Throwable) {
        TODO("Not yet implemented")
      }

    })
  }

  private fun decodeRespImage(image: String): Bitmap? {
    val byteArray: ByteArray = Base64.decode(image, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
  }
}