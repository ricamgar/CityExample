package com.utad.networking.ui.citydetail

import com.utad.networking.data.RetrofitFactory
import com.utad.networking.model.WeatherDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityDetailPresenter(val view: CityDetailView) {

    fun fetchCityDetail(cityId: Int) {
        val weatherApi = RetrofitFactory.getWeatherApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.getCityDetail(cityId)
            withContext(Dispatchers.Main) {
                view.showWeatherDetail(response.body()!!.consolidated_weather)
            }
        }
    }
}

interface CityDetailView {
    fun showWeatherDetail(detail: List<WeatherDetail>)
}