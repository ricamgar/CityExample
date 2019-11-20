package com.utad.networking.ui.citysearch

import com.utad.networking.data.local.LocalRepository
import com.utad.networking.data.remote.RemoteRepository
import com.utad.networking.model.City
import kotlinx.coroutines.*

class CitySearchPresenter(
    private val view: CitySearchView,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun searchClicked(searchTerm: String) {
        if (searchTerm.isEmpty()) return

        CoroutineScope(ioDispatcher).launch {
            try {
                val cities = remoteRepository.searchCities(searchTerm)
                withContext(mainDispatcher) {
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
        CoroutineScope(ioDispatcher).launch {
            localRepository.deleteLoggedUser()
            withContext(mainDispatcher) {
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