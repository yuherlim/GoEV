package com.example.goev.settings

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsPrivacyBinding

class SettingsPrivacyFragment : Fragment() {

    private lateinit var binding: ActivitySettingsPrivacyBinding
    private lateinit var viewModel: SettingsPrivacyViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivitySettingsPrivacyBinding.inflate(inflater, container, false)
        // initialize the viewModel property
        viewModel = ViewModelProvider(this).get(SettingsPrivacyViewModel::class.java)


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
            viewModel.deleteUser()
            dialog.dismiss()
            view?.findNavController()
                ?.navigate(R.id.action_settingsPrivacyFragment_to_loginFragment)
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }
}


