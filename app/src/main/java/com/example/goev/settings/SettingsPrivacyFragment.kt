package com.example.goev.settings

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.goev.R
import com.example.goev.database.user.UserDatabase
import com.example.goev.databinding.ActivitySettingsPrivacyBinding
import com.example.goev.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsPrivacyFragment : Fragment() {

    private lateinit var binding: ActivitySettingsPrivacyBinding
    private lateinit var viewModel: SettingsPrivacyViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivitySettingsPrivacyBinding.inflate(inflater, container, false)


        binding.accountAndLoginButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_settingsPrivacyFragment_to_settingsPrivacyAccountFragment)
        }

        binding.deleteAccountButton.setOnClickListener {
            showConfirmDialog()

        }


        binding.privacyPolicyButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_settingsPrivacyFragment_to_settingsPrivacyPolicyFragment)
        }

        return binding.root
    }

    private fun showConfirmDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_confirmation_delete_account)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.confirmButton).setOnClickListener {
            deleteAccount()
            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }

    private fun deleteAccount() {

        //delete account
        //cbbbbbb dont know how to implement

            view?.findNavController()
                ?.navigate(R.id.action_settingsPrivacyFragment_to_loginFragment)
        }
    }


