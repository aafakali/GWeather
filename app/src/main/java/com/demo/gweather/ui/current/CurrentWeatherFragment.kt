import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.demo.gweather.BuildConfig
import com.demo.gweather.R
import com.demo.gweather.data.local.WeatherDatabase
import com.demo.gweather.data.local.WeatherEntity
import com.demo.gweather.data.model.CurrentWeatherResponse
import com.demo.gweather.data.repository.HistoryRepository
import com.demo.gweather.databinding.FragmentCurrentBinding
import com.demo.gweather.ui.current.CurrentViewModel
import com.demo.gweather.utils.PrefManager
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CurrentWeatherFragment : Fragment(R.layout.fragment_current) {

    private var _binding: FragmentCurrentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurrentViewModel by viewModels()
    private lateinit var prefManager: PrefManager

    private val apiKey = BuildConfig.WEATHER_API_KEY



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentBinding.bind(view)
        prefManager = PrefManager(requireContext())

        val lat = prefManager.getLatitude()
        val lon = prefManager.getLongitude()

        if (lat != null && lon != null) {
            viewModel.fetchWeather(lat, lon, apiKey)
        }

        observeWeatherData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeWeatherData() {
        // Observe weather data
        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            weather?.let {
                val condition = it.weather.firstOrNull()

                // City name and country (from weather response)
                binding.tvCityName.text = it.name
//                binding.tvCountry.text = it.sys.country
                binding.tvCountry.text = getCountryName(it.sys.country)

                // Temperature
                binding.tvTemperature.text = "${it.main.temp.toInt()}°C"

                // Weather description
                binding.tvWeatherDesc.text =
                    condition?.description?.replaceFirstChar { c -> c.uppercase() } ?: "N/A"

                // Weather icon emoji
                binding.tvWeatherIcon.text = when (condition?.main?.lowercase()) {
                    "clear" -> "☀️"
                    "clouds" -> "☁️"
                    "rain" -> "🌧️"
                    "drizzle" -> "🌦️"
                    "thunderstorm" -> "⛈️"
                    "snow" -> "❄️"
                    "mist", "fog", "haze" -> "🌫️"
                    else -> "🌤️"
                }

                // Format sunrise and sunset times
                val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                    .withZone(ZoneId.systemDefault())

                binding.tvSunrise.text = formatter.format(Instant.ofEpochSecond(it.sys.sunrise))
                binding.tvSunset.text = formatter.format(Instant.ofEpochSecond(it.sys.sunset))

                // --- Save to DB here ---
                saveWeatherToHistory(it)

            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.tvWeatherDesc.text = "Error: $it"
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCountryName(countryCode: String): String {
        return try {
            val locale = java.util.Locale("", countryCode)
            locale.displayCountry
        } catch (e: Exception) {
            countryCode
        }
    }

    private fun saveWeatherToHistory(weather: CurrentWeatherResponse) {
        val entity = WeatherEntity(
            city = weather.name,
            country = weather.sys.country,
            temperature = weather.main.temp,
            description = weather.weather.firstOrNull()?.description ?: "N/A",
            icon = weather.weather.firstOrNull()?.main ?: "Clear",
            sunrise = weather.sys.sunrise,
            sunset = weather.sys.sunset
        )

        val dao = WeatherDatabase.getDatabase(requireContext()).weatherDao()
        val historyRepo = HistoryRepository(dao)

        lifecycleScope.launch {
            historyRepo.insertWeather(entity)
        }
    }

}