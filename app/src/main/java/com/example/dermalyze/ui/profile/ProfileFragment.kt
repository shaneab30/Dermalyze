package com.example.dermalyze.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dermalyze.databinding.FragmentProfileBinding
import com.example.dermalyze.datastore.Injection
import com.example.dermalyze.ui.login.LoginActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveUserDetails()

        binding?.logoutButton?.setOnClickListener {
            lifecycleScope.launch {
                val userPreference = Injection.provideUserRepository(requireContext()).getUserPreference()
                userPreference.saveUserToken("")
                userPreference.saveFirstName("")
                userPreference.saveEmail("")

            }
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding?.apply {
            tvMyProfile.setOnClickListener {
                moveToMyProfile()
            }
            tvPrivation.setOnClickListener {
                moveToCallService()
            }
            tvSetting.setOnClickListener {
                moveToSetting()
            }
        }
    }

    private fun retrieveUserDetails() {
        lifecycleScope.launch {
            val userPreference = Injection.provideUserRepository(requireContext()).getUserPreference()
            val firstName = userPreference.getFirstName().firstOrNull() ?: ""
            val email = userPreference.getEmail().firstOrNull() ?: ""
            binding?.tvMy?.text = firstName
            binding?.tvEmail?.text = email
        }
    }

    private fun moveToCallService() {
        val action = ProfileFragmentDirections.actionProfileFragmentToPrivacyFragment()
        findNavController().navigate(action)
    }

    private fun moveToSetting() {
        val action = ProfileFragmentDirections.actionProfileFragmentToSettingFragment()
        findNavController().navigate(action)
    }

    private fun moveToMyProfile() {
        val action = ProfileFragmentDirections.actionProfileFragmentToMyProfilFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
