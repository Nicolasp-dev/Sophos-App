package com.kotlin.sophosapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentLoginBinding
import com.kotlin.sophosapp.helpers.UserApp.Companion.prefs
import com.kotlin.sophosapp.viewModel.LoginViewModel


class LoginFragment : Fragment() {

  private lateinit var _binding: FragmentLoginBinding
  private val binding get() = _binding

  private lateinit var viewModel: LoginViewModel
  private lateinit var email: String
  private lateinit var password: String
  private var bundle = Bundle()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)


  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // SET VIEW MODEL & INFLATE LAYOUT OF THIS FRAGMENT.
    viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    _binding = FragmentLoginBinding.inflate(inflater, container, false)

    // ------------------ [ LOGIN WITH CREDENTIALS ] ----------------------- //
    _binding.loginBtn.setOnClickListener{
      email = _binding.itLoginEmail.text.toString().trim()
      password = _binding.itLoginPassword.text.toString().trim()
      viewModel.login(email, password, activity as AppCompatActivity)

      viewModel.userData.observe(viewLifecycleOwner) { user ->
        run {
          if (user!!.acceso) {
            val menuFragment = MenuFragment()
            bundle.putString("userName", user.nombre)
            menuFragment.arguments = bundle
            (activity as AppCompatActivity).supportFragmentManager.commit {
              replace(R.id.frame_container, menuFragment)
              setReorderingAllowed(true)
              addToBackStack("replacement")
            }
            Toast.makeText(activity, "Welcome Back ${user.nombre}", Toast.LENGTH_SHORT).show()
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
          if (user!!.auth) {
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