package com.utad.networking.ui.citysearch

import com.utad.networking.data.local.LocalRepository
import com.utad.networking.data.remote.RemoteRepository
import com.utad.networking.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitySearchPresenter(
    val view: CitySearchView,
    val remoteRepository: RemoteRepository,
    val localRepository: LocalRepository
) {

    fun searchClicked(searchTerm: String) {
        if (searchTerm.isEmpty()) return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cities = remoteRepository.searchCities(searchTerm)
                withContext(Dispatchers.Main) {
                    if (cities.isEmpty()) {
                        view.showEmpty()
                        return@withContext
                    }
                    view.showCities(cities)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showError()
                }
            }

        }
    }

    fun cityClicked(city: City) {
        view.openCityDetail(city.woeid)
    }

    fun logoutClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            localRepository.deleteLoggedUser()
            withContext(Dispatchers.Main) {
                view.goToLogin()
            }
        }
    }
}

interface CitySearchView {
    fun showCities(cities: List<City>)
    fun openCityDetail(woeid: Int)
    fun showError()
    fun showEmpty()
    fun goToLogin()
}