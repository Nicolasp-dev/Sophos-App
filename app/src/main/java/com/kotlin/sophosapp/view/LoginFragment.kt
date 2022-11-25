package com.kotlin.sophosapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentLoginBinding
import com.kotlin.sophosapp.viewModel.LoginViewModel

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
    viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    // INFLATE LAYOUT OF THIS FRAGMENT.
    _binding = FragmentLoginBinding.inflate(inflater, container, false)
    // BUTTON EVENT -> LOGIN.
    _binding.loginBtn.setOnClickListener{
      email = _binding.itLoginEmail.text.toString().trim()
      password = _binding.itLoginPassword.text.toString().trim()

      viewModel.login(email, password)

      viewModel.userData.observe(viewLifecycleOwner) { user ->
        run {
          if (user!!.acceso) {
            val fragment = MenuFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment).commit()
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