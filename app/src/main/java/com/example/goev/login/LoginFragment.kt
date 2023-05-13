package com.example.goev.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivityLoginBinding

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

        //button on click...
        binding.loginButton.setOnClickListener {view: View ->

            val email = binding.editLoginEmail.text.toString()
            val password = binding.editLoginPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

            viewModel.loginValidation(email, password) { success ->
                if (success) {
                    view.findNavController().navigate(R.id.action_loginFragment_to_settingsFragment)
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

    private fun clearInputFields() {
        binding.editLoginEmail.setText("")
        binding.editLoginPassword.setText("")
    }

}