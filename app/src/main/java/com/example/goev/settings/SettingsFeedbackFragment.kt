package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivityRegisterBinding
import com.example.goev.databinding.ActivitySettingsFeedbackBinding


class SettingsFeedbackFragment : Fragment() {

    private lateinit var binding: ActivitySettingsFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivitySettingsFeedbackBinding.inflate(inflater, container, false)

        binding.sendFeedbackButton.setOnClickListener {
            binding.feedbackRating.rating = 0f
            binding.editTextComment.setText("")
        }

        binding.backButton.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_settingsFeedbackFragment_to_settingsFragment)
        }

        return binding.root
    }


}