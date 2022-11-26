package com.kotlin.sophosapp.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kotlin.sophosapp.R
import com.kotlin.sophosapp.databinding.FragmentMenuBinding
import com.kotlin.sophosapp.helpers.MyToolbar

class MenuFragment : Fragment() {

  private lateinit var _binding: FragmentMenuBinding
  private val binding get() = _binding

  // ------------------------- [ON CREATE VIEW] ------------------------- //
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMenuBinding.inflate(inflater, container, false)
    return binding.root
  }

  // ------------------------- [ON CREATE] ------------------------- //
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Enable options Menu in this Fragment.
    setHasOptionsMenu(true)
  }

  // ------------------------- [ON VIEW CREATED] ------------------------- //
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if(arguments != null){
      val userName = arguments?.getString("userName").toString()
      MyToolbar().show(activity as AppCompatActivity, userName, false)
    }else{
      MyToolbar().show(activity as AppCompatActivity, "Some Name", false)
    }
    // Enable actionbar Display.
  }

  // ------------------------- [OPTION MENU SETTINGS] ------------------------- //
  // Inflate the menu.
  @Deprecated("Deprecated in Java")
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.navigation, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  // Handle click events of the menu.
  @Deprecated("Deprecated in Java")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId){
      R.id.op_send_docs -> {
        Toast.makeText(activity, "Send Documents", Toast.LENGTH_SHORT).show()
        (activity as AppCompatActivity).supportFragmentManager.commit {
          replace<SendDocumentsFragment>(R.id.frame_container)
          setReorderingAllowed(true)
          addToBackStack("replacement")
        }
        true
      } else -> super.onOptionsItemSelected(item)
    }
  }
}