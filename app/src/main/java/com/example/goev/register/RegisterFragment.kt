package com.example.goev.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.database.user.UserData
import com.example.goev.databinding.ActivityRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.registerButton.setOnClickListener {view: View ->

            val name = binding.editRegisterUserName.text.toString()
            val email = binding.editRegisterEmail.text.toString()
            val password1 = binding.editRegisterPassword.text.toString()
            val password2 = binding.editRegisterPassword2.text.toString()

            if (name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (!viewModel.validatePassword(password1, password2)) {
                Toast.makeText(
                    requireContext(),
                    "Password and confirm password do not match",
                    Toast.LENGTH_SHORT
                ).show()
                clearInputFields()
            } else {
                viewModel.isUserNameExists(name){ success ->
                    if(success){
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                "UserName exists, Please try again...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        viewModel.isUserEmailExists(email){ success->
                            if(success){
                                requireActivity().runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "User Email exists, Please try again...",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else if (!viewModel.isEmailValid(email)){
                                requireActivity().runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "Fill in the email in valid format",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else {
                                val user = UserData(0, name, email, password1,ByteArray(0), "" , "",
                                    is_logged_in = false,
                                    is_super = false
                                )
                                viewModel.addUser(user)
                                requireActivity().runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "Registered...",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                }
                            }
                        }
                    }
                }
            }

        }

        binding.signToLoginText2.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun clearInputFields() {
        binding.editRegisterUserName.setText("")
        binding.editRegisterEmail.setText("")
        binding.editRegisterPassword.setText("")
        binding.editRegisterPassword2.setText("")
    }
}