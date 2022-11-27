package com.kotlin.sophosapp.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.api.Constants
import com.kotlin.sophosapp.api.UserService
import com.kotlin.sophosapp.helpers.UserApp.Companion.prefs
import com.kotlin.sophosapp.model.RS_User
import com.kotlin.sophosapp.model.isAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor


class LoginViewModel: ViewModel() {

  private lateinit var executor: Executor
  private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo
  private lateinit var biometricPrompt: BiometricPrompt

  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
  private val service: UserService = retrofit.create(UserService::class.java)

  val userData = MutableLiveData<RS_User?>()
  val userAuth = MutableLiveData<isAuth?>()

  // ------------------ [LOGIN WITH CREDENTIALS] ----------------------- //
  fun login(email: String, password: String, activity: AppCompatActivity){
    val validEmail =  emailValidation(email)

    if(validEmail){
      val call = service.fetchCredentials(email, password)
      call.enqueue(object : Callback<RS_User>{

        override fun onResponse(call: Call<RS_User>, response: Response<RS_User>) {
          if(response.isSuccessful){
            val responseBody = response.body()
            userData.postValue(responseBody)
            prefs.storeUsername(responseBody!!.nombre)
            prefs.storeUserEmail(email)
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
    }else{
      Toast.makeText(activity, "Invalid Email", Toast.LENGTH_SHORT).show()
    }
  }

  // --------------------- [EMAIL PATTERN VALIDATION] ----------------------- //
  private fun emailValidation(email: String):Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }
  // -------------------------------------------------------------------------//

  // ------------------ [ LOGIN WITH FINGERPRINT ] ----------------------- //
  fun fingerPrintAuth(context: FragmentActivity){

    if(prefs.getUsername().isNotEmpty()){
      biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Login")
        .setSubtitle("You can enter using your biometric credentials")
        .setNegativeButtonText("Cancel")
        .build()

      executor = context.let { ContextCompat.getMainExecutor(context) }

      biometricPrompt = BiometricPrompt(context, executor, object: BiometricPrompt.AuthenticationCallback(){

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
          super.onAuthenticationError(errorCode, errString)
          Toast.makeText(context, errString.toString(), Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
          super.onAuthenticationSucceeded(result)
          Toast.makeText(context, "Biometric Authentication Success", Toast.LENGTH_SHORT).show()
          userAuth.postValue(isAuth(true))
        }

        override fun onAuthenticationFailed() {
          super.onAuthenticationFailed()
          Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
      })
      biometricPrompt.authenticate(biometricPromptInfo)
    }else{
      Toast.makeText(
        context,
        "You must login using your credential at least one time",
        Toast.LENGTH_LONG).show()
    }
  }
}
