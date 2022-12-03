package com.kotlin.sophosapp.ui.viewModel

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.sophosapp.data.network.service.UserService
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs
import com.kotlin.sophosapp.data.model.rs_user.RS_User
import com.kotlin.sophosapp.data.model.auth.UserAuth
import com.kotlin.sophosapp.utils.NonSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor


class LoginViewModel: ViewModel() {

  private lateinit var executor: Executor
  private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo
  private lateinit var biometricPrompt: BiometricPrompt

  // ____ [ MUTABLE LIVE DATA ] ____  //
  val userData = MutableLiveData<RS_User?>()
  val userAuth = MutableLiveData<UserAuth?>()

  // ____________________ [ LOGIN WITH CREDENTIALS ] ____________________ \\
  fun login(email: String, password: String, activity: AppCompatActivity){
    val validEmail =  emailValidation(email)

    if(validEmail){
      UserService().fetchUserData(email, password).enqueue(object : Callback<RS_User>{

        override fun onResponse(call: Call<RS_User>, response: Response<RS_User>) {
          if(response.isSuccessful){
            val responseBody = response.body()
            userData.postValue(responseBody)
            prefs.storeUsername(responseBody!!.nombre)
            prefs.storeUserEmail(email)
          }else {
            NonSuccessResponse().message(response.code())
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
          userAuth.postValue(UserAuth(true))
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
