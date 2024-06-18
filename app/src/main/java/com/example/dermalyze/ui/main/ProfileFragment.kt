package com.example.dermalyze.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}