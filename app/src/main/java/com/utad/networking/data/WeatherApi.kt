package com.utad.networking.data

import com.utad.networking.model.City
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("location/search/")
    suspend fun searchCities(@Query("query") searchText: String): Response<List<City>>

    @GET("api/location/")
    suspend fun searchCity(@Path("woeid") id: Int): Response<List<Day>>
}


object RetrofitFactory {
    const val BASE_URL = "https://www.metaweather.com/api/"

    fun getWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(WeatherApi::class.java)
    }
}