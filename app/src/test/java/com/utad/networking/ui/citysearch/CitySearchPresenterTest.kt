package com.utad.networking.ui.citysearch

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.utad.networking.data.local.LocalRepository
import com.utad.networking.data.remote.RemoteRepository
import com.utad.networking.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitySearchPresenterTest {

    private lateinit var presenter: CitySearchPresenter
    @Mock private lateinit var remoteRepository: RemoteRepository
    @Mock private lateinit var localRepository: LocalRepository
    @Mock private lateinit var view: CitySearchView

    @Before
    fun setUp() {
        presenter = CitySearchPresenter(
            view,
            remoteRepository,
            localRepository,
            Dispatchers.Unconfined,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun shouldCallEmptyWhenEmptyResults() = runBlocking {
        //configuracion
        val searchTerm = "a"
        whenever(remoteRepository.searchCities(searchTerm)).thenReturn(emptyList())

        //ejecucion
        presenter.searchClicked(searchTerm)

        //comprobacion
        verify(view).showEmpty()
    }

    @Test
    fun shouldShowCitiesWhenResultsNotEmpty() = runBlocking {
        val searchTerm = "a"
        val citiesList = listOf(City(1, "city"))
        whenever(remoteRepository.searchCities(searchTerm)).thenReturn(citiesList)

        presenter.searchClicked(searchTerm)

        verify(view).showCities(citiesList)
    }

    @Test
    fun shouldShowLoadingWhileIsLoading() = runBlocking {
        val searchTerm = "a"
        whenever(remoteRepository.searchCities(searchTerm)).thenReturn(emptyList())

        presenter.searchClicked(searchTerm)

        verify(view).showEmpty()
    }


    //
//    private lateinit var presenter: CitySearchPresenter
//    @Mock private lateinit var remoteRepository: RemoteRepository
//    @Mock private lateinit var localRepository: LocalRepository
//    @Mock private lateinit var view: CitySearchView
//
//
//    @Before
//    fun setUp() {
//        presenter = CitySearchPresenter(
//            view,
//            remoteRepository,
//            localRepository,
//            Dispatchers.Unconfined,
//            Dispatchers.Unconfined
//        )
//    }
//
//    @Test
//    fun searchClickedShouldShowResultsIfExists() = runBlocking {
//        val city = City(123, "City")
//        val search = "searchTerm"
//        whenever(remoteRepository.searchCities(search)).thenReturn(listOf(city))
//
//        presenter.searchClicked(search)
//
//        verify(view).showCities(listOf(city))
//
//    }
//
//    @Test(expected = Exception::class)
//    fun shouldShowErrorWhenSearchFails() = runBlocking {
//        whenever(remoteRepository.searchCities("")).thenThrow()
//
//        presenter.searchClicked("")
//
//        verify(view).showError()
//    }
//
//    @Test
//    fun shouldShowLoadingWhileDataIsLoading() = runBlocking {
//        val city = City(123, "City")
//        val search = "searchTerm"
//        whenever(remoteRepository.searchCities(search)).thenReturn(listOf(city))
//
//        presenter.searchClicked(search)
//
//        verify(view).showLoading()
//        verify(view).showCities(listOf(city))
//        verify(view).hideLoading()
//
//    }
}