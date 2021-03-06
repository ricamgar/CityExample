package com.utad.networking.data.remote

import com.utad.networking.model.City
import com.utad.networking.model.User
import com.utad.networking.model.WeatherResponse

interface RemoteRepository {
    suspend fun login(username: String, password: String): User?
    suspend fun searchCities(term: String): List<City>
    suspend fun getCityDetail(id: Int): WeatherResponse
}