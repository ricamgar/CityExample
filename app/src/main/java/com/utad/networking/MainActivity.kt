package com.utad.networking

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.data.RetrofitFactory
import com.utad.networking.model.City
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

	private lateinit var citySearch: List<City>
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		citiesRecyclerView.layoutManager = LinearLayoutManager(this)
		citiesRecyclerView.setHasFixedSize(true)
		val citiesAdapter = CitiesAdapter {
			Toast.makeText(this, "${it.title} clicked!!", Toast.LENGTH_SHORT).show()
		}
		citiesRecyclerView.adapter = citiesAdapter
		
		val weatherApi = RetrofitFactory.getWeatherApi()
		CoroutineScope(Dispatchers.IO).launch {
			val response = weatherApi.searchCities()
			citySearch = response.body()!!
			withContext(Dispatchers.Main) {
				citiesAdapter.addCities(citySearch)
			}
		}
		
		SearchButton.setOnClickListener {
			CoroutineScope(Dispatchers.IO).launch {
				withContext(Dispatchers.Main) {
					var cityFiltered: ArrayList<City> = ArrayList<City>()
					for (City in citySearch) {
						if (City.title.contains(searchText.text.toString(), true)) {
							cityFiltered.add(City)
						}
					}
					citiesAdapter.addCities(cityFiltered)
				}
			}
		}
	}
}
