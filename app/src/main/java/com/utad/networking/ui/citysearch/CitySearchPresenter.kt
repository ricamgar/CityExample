package com.utad.networking.ui.citysearch

import com.utad.networking.data.RetrofitFactory
import com.utad.networking.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitySearchPresenter(val view: CitySearchView) {

    fun searchClicked(searchTerm: String) {
        if (searchTerm.isEmpty()) return

        val weatherApi = RetrofitFactory.getWeatherApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.searchCities(searchTerm)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val cities = response.body()!!
                    if (cities.isEmpty()) {
                        view.showEmpty()
                        return@withContext
                    }
                    view.showCities(response.body()!!)
                } else {
                    view.showError()
                }
            }
        }
    }

    fun cityClicked(city: City) {
        view.openCityDetail(city.woeid)
    }
}

interface CitySearchView {
    fun showCities(cities: List<City>)
    fun openCityDetail(woeid: Int)
    fun showError()
    fun showEmpty()
}