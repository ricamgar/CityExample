package com.utad.networking

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.data.RetrofitFactory
import com.utad.networking.data.WeatherApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var searchText : EditText
    lateinit var searchButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById(R.id.searchText)
        searchButton = findViewById(R.id.searchButton)
        val search = searchText.text.toString()

        citiesRecyclerView.layoutManager = LinearLayoutManager(this)
        citiesRecyclerView.setHasFixedSize(true)
        val citiesAdapter = CitiesAdapter {
            Toast.makeText(this, "${it.title} clicked!!", Toast.LENGTH_SHORT).show()
        }
        citiesRecyclerView.adapter = citiesAdapter

        val weatherApi = RetrofitFactory.getWeatherApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.searchCities()
            withContext(Dispatchers.Main) {
                citiesAdapter.addCities(response.body()!!)
            }
        }

        searchButton.setOnClickListener(){

        }
    }
}
