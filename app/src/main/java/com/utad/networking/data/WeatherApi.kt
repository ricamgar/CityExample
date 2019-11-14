package com.utad.networking.data

import com.utad.networking.model.City
import com.utad.networking.model.WeatherDetail
import com.utad.networking.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface WeatherApi {
    @GET("location/search/")
    suspend fun searchCities(@Query("query") term: String): Response<List<City>>

    @GET("/api/location/{id}/")
    suspend fun getCityDetail(@Path("id") cityId: Int?): Response<WeatherResponse>
}


object RetrofitFactory {
    const val BASE_URL = "https://www.metaweather.com/api/"

    fun getWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(WeatherApi::class.java)
    }

    fun login(username: String, password: String): Boolean {
        if (username.equals("root") && password.equals("1234")) {
            return true
        }
        return false
    }
}