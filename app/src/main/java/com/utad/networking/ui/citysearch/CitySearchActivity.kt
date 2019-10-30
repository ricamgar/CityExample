package com.utad.networking.ui.citysearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
}
