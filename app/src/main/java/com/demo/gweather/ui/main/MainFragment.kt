package com.demo.gweather

import CurrentWeatherFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.gweather.databinding.FragmentMainBinding
import com.demo.gweather.ui.history.HistoryFragment
import com.demo.gweather.utils.PrefManager
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        val prefManager = PrefManager(requireContext())
        val username = prefManager.run {
            if (isLoggedIn()) prefs.getString("username", "Guest") else "Guest"
        }

        binding.tvUserName.text = username

        val adapter = WeatherPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = if (pos == 0) "Current" else "History"
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class WeatherPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CurrentWeatherFragment()
                else -> HistoryFragment()
            }
        }
    }
}
