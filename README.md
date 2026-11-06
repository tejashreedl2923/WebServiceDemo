# Experiment 12: Access Web Services Using HTTP Requests in Android

## Student Details

**Name:** Tejashree DL  
**USN:** 25MCAR0124  
**Experiment No.:** 12  
**Experiment Title:** Access Web Services Using HTTP Requests in Android

## Aim

To develop an Android application using Kotlin that accesses web services through HTTP requests, retrieves weather information from an online API, processes JSON responses, and displays the information in a user-friendly interface.

## Description

This experiment demonstrates how an Android application communicates with external web services using HTTP requests.

The application uses the Open-Meteo Weather API to retrieve actual weather information for different cities. The Open-Meteo Geocoding API is used to obtain the geographical coordinates of the entered city.

The application displays the following weather information:

- City name and country
- Current temperature in Celsius
- Weather condition
- Humidity percentage
- Feels-like temperature
- Wind speed

The application uses HTTP GET requests to retrieve information from web services and processes JSON responses to extract the required weather details.

## Software Requirements

- Android Studio
- Kotlin
- XML
- Android SDK
- Internet connection
- Android Emulator or physical Android device

## APIs Used

### 1. Open-Meteo Geocoding API

The Geocoding API is used to search for a city and retrieve its geographical coordinates.

API Endpoint:

https://geocoding-api.open-meteo.com/v1/search

### 2. Open-Meteo Weather API

The Weather API is used to retrieve weather information based on the geographical coordinates of the city.

API Endpoint:

https://api.open-meteo.com/v1/forecast

The following weather parameters are used:

- temperature_2m
- relative_humidity_2m
- apparent_temperature
- weather_code
- wind_speed_10m

The basic non-commercial use of these APIs does not require an API key.

## Procedure

1. Open Android Studio.
2. Create a new Android project using the Empty Views Activity template.
3. Select Kotlin as the programming language.
4. Configure the application name and minimum SDK.
5. Add Internet permission in AndroidManifest.xml.
6. Design the user interface using XML.
7. Create an EditText to enter the city name.
8. Create a Button to search for weather information.
9. Create TextViews to display the weather details.
10. Implement HTTP GET requests using HttpURLConnection.
11. Use the Geocoding API to obtain the latitude and longitude of the entered city.
12. Send another HTTP GET request to the Weather API.
13. Receive the JSON response from the server.
14. Parse the JSON response using JSONObject.
15. Extract temperature, humidity, weather condition, feels-like temperature, and wind speed.
16. Display the weather information in the Android application.
17. Run the application on an emulator or physical Android device.
18. Test the application by entering different city names.

## Implementation

### AndroidManifest.xml

Internet permission is required to access the web services.

    <uses-permission android:name="android.permission.INTERNET" />

### MainActivity.kt

The MainActivity contains the implementation for:

- Initializing the user interface.
- Accepting the city name.
- Sending HTTP GET requests.
- Retrieving geographical coordinates.
- Accessing weather information.
- Parsing JSON responses.
- Displaying weather details.
- Handling errors and invalid city names.

### activity_main.xml

The XML layout contains:

- Weather Dashboard heading
- City input field
- Search button
- Current weather card
- Temperature display
- Weather condition
- Humidity card
- Feels-like temperature card
- Wind speed card
- Weather status message

## Working Principle

1. The user enters a city name.
2. The application sends an HTTP GET request to the Geocoding API.
3. The server returns the geographical coordinates of the city in JSON format.
4. The application extracts the latitude and longitude.
5. The application sends another HTTP GET request to the Weather API.
6. The Weather API returns the current weather information in JSON format.
7. The application parses the JSON response.
8. The extracted weather details are displayed on the screen.

## Output

The application displays a weather dashboard containing:

- City name and country
- Temperature in Celsius
- Weather condition
- Humidity percentage
- Feels-like temperature
- Wind speed

The application successfully retrieves weather information for different cities using HTTP requests.

## Screenshots

### 1. Weather Dashboard

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/fb713e68-5a3b-49c6-ae84-6da48874e98f" />


### 2. City Search

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/4c0cf059-2ea8-4dd2-88b3-e93966e506b5" />

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/158e00ca-75d7-463f-ad26-2d0ede435291" />

### 3. Weather Information

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/5e439cd7-63d3-4135-acda-d2f20ec6049a" />


## Result

The Android application was successfully developed using Kotlin and XML.

The application accesses web services using HTTP GET requests, retrieves weather information from the Open-Meteo API, processes JSON responses, and displays weather details for different cities.

Thus, the experiment successfully demonstrates accessing web services using HTTP requests in Android.

## Conclusion

This experiment helped in understanding communication between an Android application and an external web service.

It demonstrated the use of HTTP GET requests, Internet permission, JSON parsing, API integration, and displaying dynamic data in an Android application.

## Learning Outcomes

- Learned how to access web services from Android.
- Understood the working of HTTP GET requests.
- Learned how to use HttpURLConnection.
- Understood how to retrieve and process JSON data.
- Learned how to integrate external APIs into Android applications.
- Learned how to display dynamic weather information.
- Understood the importance of Internet permission and error handling.
