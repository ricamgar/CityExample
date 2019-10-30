package com.utad.networking.model

data class WeatherResponse(
    val consolidated_weather: List<WeatherDetail>
)

data class WeatherDetail(
    val min_temp: Double,
    val max_temp: Double
)