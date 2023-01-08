package com.kotlin.sophosapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentLoginBinding
import com.kotlin.sophosapp.utils.Routing
import com.kotlin.sophosapp.ui.viewModel.LoginViewModel
import com.kotlin.sophosapp.utils.Constants
import com.kotlin.sophosapp.utils.UserApp.Companion.prefs


class LoginFragment : Fragment() {

  private lateinit var _binding: FragmentLoginBinding
  private val binding get() = _binding
  private lateinit var viewModel: LoginViewModel

  private lateinit var email: String
  private lateinit var password: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // SET VIEW MODEL & INFLATE LAYOUT OF THIS FRAGMENT.
    viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    _binding = FragmentLoginBinding.inflate(inflater, container, false)

    if(prefs.getStoreTheme() == Constants.LIGHT_THEME){
      _binding.ivMain.setImageResource(R.drawable.sophos_logo_light)
    }else{
      _binding.ivMain.setImageResource(R.drawable.sophos_logo_dark)
    }

    // ------------------ [ LOGIN WITH CREDENTIALS ] ----------------------- //
    _binding.loginBtn.setOnClickListener{
      email = _binding.itLoginEmail.text.toString().trim()
      password = _binding.itLoginPassword.text.toString().trim()

      viewModel.login(email, password, activity as AppCompatActivity)

      viewModel.userData.observe(viewLifecycleOwner) { user ->
        run {
          if (user!!.access) {
            Routing.goTo(activity as AppCompatActivity, MenuFragment())
            Toast.makeText(activity, "Welcome Back ${user.name}", Toast.LENGTH_SHORT).show()
          } else {
            Toast.makeText(activity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }

    // ------------------ [ LOGIN WITH FINGERPRINT ] ----------------------- //
    _binding.btnFingerprintLogin.setOnClickListener{

      activity?.let {it1 -> viewModel.fingerPrintAuth(it1)}

      viewModel.userAuth.observe(viewLifecycleOwner){ user ->
        run {
          if (user!!.isAuth) {
            Routing.goTo(activity as AppCompatActivity, MenuFragment())
            Toast.makeText(activity, "Welcome Back", Toast.LENGTH_SHORT).show()
          } else {
            Toast.makeText(activity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
    return binding.root
  }
}