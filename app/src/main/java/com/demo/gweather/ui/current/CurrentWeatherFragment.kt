

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.gweather.R
import com.demo.gweather.databinding.FragmentCurrentBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CurrentWeatherFragment : Fragment(R.layout.fragment_current) {
    private var _binding: FragmentCurrentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<com.example.gweather.ui.current.CurrentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCurrentBinding.bind(view)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
