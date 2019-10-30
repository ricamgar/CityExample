package com.utad.networking.ui.citysearch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.R
import com.utad.networking.model.City
import com.utad.networking.ui.citydetail.CityDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class CitySearchActivity : AppCompatActivity(), CitySearchView {

    lateinit var citiesAdapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = CitySearchPresenter(this)

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

    override fun showCities(cities: List<City>) {
        citiesAdapter.addCities(cities)
    }

    override fun openCityDetail(woeid: Int) {
        val intent = Intent(this, CityDetailActivity::class.java)
        intent.putExtra("city_id", woeid)
        startActivity(intent)
    }
}
