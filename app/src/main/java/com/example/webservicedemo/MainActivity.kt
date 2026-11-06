package com.example.webservicedemo

import android.os.Bundle
import android.os.StrictMode
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var cityInput: EditText
    private lateinit var getWeatherButton: Button

    private lateinit var cityName: TextView
    private lateinit var weatherIcon: TextView
    private lateinit var temperature: TextView
    private lateinit var weatherCondition: TextView
    private lateinit var humidity: TextView
    private lateinit var feelsLike: TextView
    private lateinit var windSpeed: TextView
    private lateinit var weatherResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityInput = findViewById(R.id.cityInput)
        getWeatherButton = findViewById(R.id.getWeatherButton)

        cityName = findViewById(R.id.cityName)
        weatherIcon = findViewById(R.id.weatherIcon)
        temperature = findViewById(R.id.temperature)
        weatherCondition = findViewById(R.id.weatherCondition)
        humidity = findViewById(R.id.humidity)
        feelsLike = findViewById(R.id.feelsLike)
        windSpeed = findViewById(R.id.windSpeed)
        weatherResult = findViewById(R.id.weatherResult)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build()
        )

        getWeatherButton.setOnClickListener {
            searchWeather()
        }

        cityInput.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchWeather()
                true
            } else {
                false
            }
        }
    }

    private fun searchWeather() {

        val city = cityInput.text.toString().trim()

        if (city.isEmpty()) {
            weatherResult.text = "Please enter a city name"
            return
        }

        getWeatherButton.isEnabled = false
        getWeatherButton.text = "Searching..."
        weatherResult.text = "Fetching weather information..."

        try {

            val encodedCity = URLEncoder.encode(city, "UTF-8")

            val geoUrl = URL(
                "https://geocoding-api.open-meteo.com/v1/search" +
                        "?name=$encodedCity" +
                        "&count=1" +
                        "&language=en" +
                        "&format=json"
            )

            val geoConnection =
                geoUrl.openConnection() as HttpURLConnection

            geoConnection.requestMethod = "GET"
            geoConnection.connectTimeout = 15000
            geoConnection.readTimeout = 15000

            val geoResponseCode = geoConnection.responseCode

            if (geoResponseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("Geocoding HTTP Error: $geoResponseCode")
            }

            val geoResponse =
                geoConnection.inputStream.bufferedReader().use {
                    it.readText()
                }

            geoConnection.disconnect()

            val geoJson = JSONObject(geoResponse)

            if (!geoJson.has("results")) {
                weatherResult.text = "City not found. Try another city."
                return
            }

            val results = geoJson.getJSONArray("results")

            if (results.length() == 0) {
                weatherResult.text = "City not found. Try another city."
                return
            }

            val location = results.getJSONObject(0)

            val latitude = location.getDouble("latitude")
            val longitude = location.getDouble("longitude")

            val city = location.getString("name")

            val country = location.optString("country", "")

            fetchWeather(latitude, longitude, city, country)

        } catch (e: Exception) {

            weatherResult.text =
                "Error: ${e.message ?: "Unable to fetch weather"}"

        } finally {

            getWeatherButton.isEnabled = true
            getWeatherButton.text = "Search"
        }
    }

    private fun fetchWeather(
        latitude: Double,
        longitude: Double,
        city: String,
        country: String
    ) {

        try {

            val weatherUrl = URL(
                "https://api.open-meteo.com/v1/forecast" +
                        "?latitude=$latitude" +
                        "&longitude=$longitude" +
                        "&current=temperature_2m," +
                        "relative_humidity_2m," +
                        "apparent_temperature," +
                        "weather_code," +
                        "wind_speed_10m" +
                        "&timezone=auto"
            )

            val connection =
                weatherUrl.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            val responseCode = connection.responseCode

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("Weather HTTP Error: $responseCode")
            }

            val response =
                connection.inputStream.bufferedReader().use {
                    it.readText()
                }

            connection.disconnect()

            val json = JSONObject(response)

            val current = json.getJSONObject("current")

            val temp = current.getDouble("temperature_2m")
            val humidityValue =
                current.getInt("relative_humidity_2m")

            val feelsValue =
                current.getDouble("apparent_temperature")

            val windValue =
                current.getDouble("wind_speed_10m")

            val code =
                current.getInt("weather_code")

            val condition = getWeatherCondition(code)
            val icon = getWeatherIcon(code)

            cityName.text =
                if (country.isNotEmpty()) {
                    "$city, $country"
                } else {
                    city
                }

            weatherIcon.text = icon

            temperature.text = "${temp}°C"

            weatherCondition.text = condition

            humidity.text = "$humidityValue%"

            feelsLike.text = "${feelsValue}°C"

            windSpeed.text = "${windValue} km/h"

            weatherResult.text =
                "Weather updated successfully • HTTP $responseCode"

        } catch (e: Exception) {

            weatherResult.text =
                "Error: ${e.message ?: "Unable to fetch weather"}"
        }
    }

    private fun getWeatherCondition(code: Int): String {

        return when (code) {

            0 -> "Clear Sky"

            1 -> "Mainly Clear"

            2 -> "Partly Cloudy"

            3 -> "Overcast"

            45, 48 -> "Fog"

            51, 53, 55 -> "Drizzle"

            56, 57 -> "Freezing Drizzle"

            61, 63, 65 -> "Rain"

            66, 67 -> "Freezing Rain"

            71, 73, 75 -> "Snowfall"

            77 -> "Snow Grains"

            80, 81, 82 -> "Rain Showers"

            85, 86 -> "Snow Showers"

            95 -> "Thunderstorm"

            96, 99 -> "Thunderstorm with Hail"

            else -> "Unknown Weather"
        }
    }

    private fun getWeatherIcon(code: Int): String {

        return when (code) {

            0 -> "☀️"

            1 -> "🌤️"

            2 -> "⛅"

            3 -> "☁️"

            45, 48 -> "🌫️"

            51, 53, 55, 56, 57 -> "🌦️"

            61, 63, 65, 66, 67 -> "🌧️"

            71, 73, 75, 77 -> "❄️"

            80, 81, 82 -> "🌦️"

            85, 86 -> "🌨️"

            95, 96, 99 -> "⛈️"

            else -> "🌡️"
        }
    }
}