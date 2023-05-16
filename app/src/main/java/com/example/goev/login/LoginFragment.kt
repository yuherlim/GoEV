package com.example.goev.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.MainActivity
import com.example.goev.R
import com.example.goev.databinding.ActivityLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        binding = ActivityLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.updateAllUsersLoggedOut()

        //check whether database is empty
        viewModel.getRowCount { count ->
            if (count < 1) {
                viewModel.insertSampleData()
            }
        }

        //button on click...
        binding.loginButton.setOnClickListener {view: View ->

            val email = binding.editLoginEmail.text.toString()
            val password = binding.editLoginPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

            viewModel.loginValidation(email, password) { success ->
                if (success) {
                    view.findNavController().navigate(R.id.action_loginFragment_to_trackerFragment)
                    Toast.makeText(requireContext(), "Login....", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Wrong email or wrong password", Toast.LENGTH_SHORT).show()
                    clearInputFields()
                }
            }
        }

        binding.loginToSignText2.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onResume() {
        // Hides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        (requireActivity() as MainActivity).hideTopAppBar()
        super.onResume()
    }

    override fun onPause() {
        // Unhidden bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        (requireActivity() as MainActivity).showTopAppBar()
        super.onPause()
    }

    private fun clearInputFields() {
        binding.editLoginEmail.setText("")
        binding.editLoginPassword.setText("")
    }

}