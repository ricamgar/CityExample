package com.utad.networking.data.remote

import android.accounts.NetworkErrorException
import com.utad.networking.data.WeatherApi
import com.utad.networking.model.City
import com.utad.networking.model.WeatherResponse

class RetrofitRemoteRepository(val weatherApi: WeatherApi) : RemoteRepository {

    override suspend fun searchCities(term: String): List<City> {
        val response = weatherApi.searchCities(term)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw NetworkErrorException()
        }
    }

    override suspend fun getCityDetail(id: Int): WeatherResponse {
        return weatherApi.getCityDetail(id).body()!!
    }

}
