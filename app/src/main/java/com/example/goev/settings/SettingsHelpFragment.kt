package com.example.goev.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivityRegisterBinding
import com.example.goev.databinding.ActivitySettingsHelpBinding

class SettingsHelpFragment : Fragment() {

    private lateinit var binding: ActivitySettingsHelpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivitySettingsHelpBinding.inflate(inflater, container, false)

        val name = binding.contactNameTitle.text.toString()
        val email = binding.contactEmailTitle.text.toString()
        val content = binding.contactMessageTitle.text.toString()

        binding.sendMessageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:khokajie01@gmail.com") // Set the recipient email address
                putExtra(Intent.EXTRA_SUBJECT, "Contact Inquiry") // Set the email subject
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Sender Email: $email\nSender Name: $name\nContent: $content"
                ) // Set the email body with the sender's email, name, and content
            }

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }

            binding.editTextContactEmail.setText("")
            binding.editTextContactName.setText("")
            binding.editTextContactMessage.setText("")
        }

        binding.contactToCallText2.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:0123456789".toUri()
            startActivity(intent)
        }

        binding.backButton.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_settingsHelpFragment_to_settingsFragment)
        }


        return binding.root
    }

}