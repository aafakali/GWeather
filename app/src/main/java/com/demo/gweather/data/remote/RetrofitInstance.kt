import com.demo.gweather.data.remote.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val GEO_BASE_URL = "https://api.openweathermap.org/"

    val weatherApi: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    val geoApi: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(GEO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}
