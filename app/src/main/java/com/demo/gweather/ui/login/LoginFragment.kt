package com.demo.gweather.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.gweather.R
import com.demo.gweather.databinding.FragmentLoginBinding
import com.demo.gweather.utils.PrefManager

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentLoginBinding.bind(view)
        prefManager = PrefManager(requireContext())

        // ✅ Auto-navigate if already logged in
        if (prefManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_login_to_main)
            return
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefManager.saveLogin(username)
            findNavController().navigate(R.id.action_login_to_main)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
