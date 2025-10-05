package com.demo.gweather.ui.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.gweather.R
import com.demo.gweather.databinding.FragmentLoginBinding
import com.demo.gweather.utils.PrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var hasLocationPermission = false

    private val requestLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (!hasLocationPermission) {
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentLoginBinding.bind(view)
        prefManager = PrefManager(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (prefManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_login_to_main)
            return
        }

        if (!hasLocationPermission()) {
            requestLocationPermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            hasLocationPermission = true
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!hasLocationPermission) {
                Toast.makeText(requireContext(), "Please grant location permission", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fetchLocationAndLogin(username)
        }
    }

    private fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationAndLogin(username: String) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude

                prefManager.saveLogin(username)
                prefManager.saveLocation(lat, lon)

                findNavController().navigate(R.id.action_login_to_main)

            } else {
                Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
