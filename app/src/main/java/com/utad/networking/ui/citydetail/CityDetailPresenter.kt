package com.utad.networking.ui.citydetail

import com.utad.networking.data.remote.RemoteRepository
import com.utad.networking.model.WeatherDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityDetailPresenter(
    private val view: CityDetailView,
    private val remoteRepository: RemoteRepository
) {

    fun fetchCityDetail(cityId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val city = withContext(Dispatchers.IO) { remoteRepository.getCityDetail(cityId) }
                view.showWeatherDetail(city.consolidated_weather)
            } catch (e: Exception) {
                view.showError()
            }
        }
    }
}

interface CityDetailView {
    fun showWeatherDetail(detail: List<WeatherDetail>)
    fun showError()
}