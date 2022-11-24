package com.kotlin.sophosapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.button.MaterialButton
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // INFLATE LAYOUT OF THIS FRAGMENT.
    val view = inflater.inflate(R.layout.fragment_login, container, false)
    // BUTTON EVENT -> LOGIN.
    val loginBtn: MaterialButton = view.findViewById(R.id.login_btn)
    loginBtn.setOnClickListener{
      val fragment = MenuFragment()
      val transaction = parentFragmentManager.beginTransaction()
      transaction.replace(R.id.frame_container, fragment).commit()
    }
    return view
  }
}