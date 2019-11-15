package com.utad.networking.ui.citysearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.R
import com.utad.networking.data.remote.RetrofitFactory
import com.utad.networking.data.local.PreferenceLocalRepository
import com.utad.networking.data.remote.RemoteRepository
import com.utad.networking.data.remote.RetrofitRemoteRepository
import com.utad.networking.model.City
import com.utad.networking.ui.citydetail.CityDetailActivity
import com.utad.networking.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class CitySearchActivity : AppCompatActivity(), CitySearchView {

    lateinit var citiesAdapter: CitiesAdapter
    lateinit var presenter: CitySearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val remoteRepository: RemoteRepository =
            RetrofitRemoteRepository(RetrofitFactory.getWeatherApi())
        val localRepository =
            PreferenceLocalRepository(
                getSharedPreferences(
                    "login_preference",
                    Context.MODE_PRIVATE
                )
            )
        presenter = CitySearchPresenter(this, remoteRepository, localRepository)

        citiesRecyclerView.layoutManager = LinearLayoutManager(this)
        citiesRecyclerView.setHasFixedSize(true)
        citiesAdapter = CitiesAdapter {
            presenter.cityClicked(it)
        }
        citiesRecyclerView.adapter = citiesAdapter

        searchBtn.setOnClickListener {
            val searchTerm = queryTxt.text.toString()
            presenter.searchClicked(searchTerm)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_item -> {
                presenter.logoutClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showCities(cities: List<City>) {
        citiesAdapter.addCities(cities)
        citiesRecyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    override fun openCityDetail(woeid: Int) {
        val intent = Intent(this, CityDetailActivity::class.java)
        intent.putExtra("city_id", woeid)
        startActivity(intent)
    }

    override fun showError() {
        Toast.makeText(this, "Error fetching cities", Toast.LENGTH_SHORT).show()
    }

    override fun showEmpty() {
        emptyView.visibility = View.VISIBLE
        citiesRecyclerView.visibility = View.GONE
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
