package com.utad.networking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utad.networking.data.RetrofitFactory
import com.utad.networking.model.City
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityDetailActivity : AppCompatActivity() {

    private lateinit var weatherSearch: List<City>
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_city_detail)
        val id = intent.extras.getString("id")

        val citiesAdapter = CitiesAdapter {
            Toast.makeText(this, "${it.title} clicked!!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CityDetailActivity::class.java)
            intent.putExtra("id",it.woeid)
            startActivity(intent)
        }

        val weatherApi = RetrofitFactory.getWeatherApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.getWeather(id)
            weatherSearch = response.body()!!
            withContext(Dispatchers.Main) {
                citiesAdapter.addCities(weatherSearch)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                var cityFiltered: ArrayList<City> = ArrayList<City>()
                for (City in weatherSearch) {
                    if (City.title.contains(searchText.text.toString(), true)) {
                        cityFiltered.add(City)
                    }
                }
                //weatherSearch.addCities(cityFiltered)
            }
        }
	}
}
