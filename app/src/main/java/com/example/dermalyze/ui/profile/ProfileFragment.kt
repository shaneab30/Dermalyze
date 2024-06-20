package com.example.dermalyze.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dermalyze.databinding.FragmentProfileBinding
import com.example.dermalyze.datastore.Injection
import com.example.dermalyze.ui.login.LoginActivity
import kotlinx.coroutines.runBlocking


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? =null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.logoutButton?.setOnClickListener {
            runBlocking {
                val userPreference = Injection.provideUserRepository(requireContext()).getUserPreference()
                userPreference.saveUserToken("")
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

    private fun moveToCallService() {
        val interaction =
            com.example.dermalyze.ui.main.ProfileFragmentDirections.actionProfileFragmentToPrivacyFragment()
        findNavController()
            .navigate(interaction)    }

    private fun moveToSetting() {
        val interaction =
            com.example.dermalyze.ui.main.ProfileFragmentDirections.actionProfileFragmentToSettingFragment()
        findNavController().navigate(interaction)
    }

    private fun moveToMyProfile() {
        val interaction =
            com.example.dermalyze.ui.main.ProfileFragmentDirections.actionProfileFragmentToMyProfilFragment()
        findNavController().navigate(interaction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}