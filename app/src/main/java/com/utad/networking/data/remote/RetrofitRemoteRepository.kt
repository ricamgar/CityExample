package com.utad.networking.data.remote

import android.accounts.NetworkErrorException
import com.utad.networking.model.City
import com.utad.networking.model.User
import com.utad.networking.model.WeatherResponse

class RetrofitRemoteRepository(val weatherApi: WeatherApi) : RemoteRepository {

    override suspend fun login(username: String, password: String): User? {
        if (username.equals("root") && password.equals("1234")) {
            return User(username, password)
        } else {
            return null
        }
    }

    override suspend fun searchCities(term: String): List<City> {
        val response = weatherApi.searchCities(term)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw NetworkErrorException()
        }
    }

    override suspend fun getCityDetail(id: Int): WeatherResponse {
        val response = weatherApi.getCityDetail(id)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw NetworkErrorException()
        }
    }
}
