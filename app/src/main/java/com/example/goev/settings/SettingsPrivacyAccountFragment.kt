package com.example.goev.settings

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsPrivacyAccountBinding

class SettingsPrivacyAccountFragment : Fragment() {

    private lateinit var binding: ActivitySettingsPrivacyAccountBinding
    private lateinit var viewModel: SettingsPrivacyAccountViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivitySettingsPrivacyAccountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsPrivacyAccountViewModel::class.java)

        binding.changeFullNameButton.setOnClickListener {
            showConfirmUserNameDialog()
        }

        binding.changeEmailButton.setOnClickListener {
            showConfirmEmailDialog()
        }

        binding.changePasswordButton.setOnClickListener {
            showConfirmPasswordDialog()
        }


        return binding.root
    }


    private fun showConfirmUserNameDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_change_username)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.confirmButton).setOnClickListener {
            val newUserName = dialog.findViewById<EditText>(R.id.editTextUserName).text.toString()
            if(newUserName == ""){
                Toast.makeText(
                    requireContext(),
                    "Fill in the Username before you click the confirm",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                viewModel.updateUserName(newUserName)
                dialog.dismiss()
            }
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }

    private fun showConfirmEmailDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_change_email)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.confirmButton).setOnClickListener {
            val newUserEmail = dialog.findViewById<EditText>(R.id.editTextEmail).text.toString()
            if(newUserEmail == ""){
                Toast.makeText(
                    requireContext(),
                    "Fill in the email before you click the confirm",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                viewModel.updateUserEmail(newUserEmail)
                dialog.dismiss()
            }
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }

    private fun showConfirmPasswordDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_change_password)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.confirmButton).setOnClickListener {

            val newUserPassword1 = dialog.findViewById<EditText>(R.id.editTextPassword).text.toString()
            val newUserPassword2 = dialog.findViewById<EditText>(R.id.editTextPassword2).text.toString()

                if (!viewModel.validatePassword(newUserPassword1, newUserPassword2)) {
                    Toast.makeText(
                        requireContext(),
                        "Password and confirm password do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.updateUserPassword(newUserPassword1)
                    dialog.dismiss()
                }

        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }
}