package com.utad.networking

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.data.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.utad.networking.CitiesAdapter as CitiesAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mySearchView = findViewById<SearchView>(R.id.searchViewMain)
        val weatherApi = RetrofitFactory.getWeatherApi()

        val citiesAdapter = CitiesAdapter {
            val intent = Intent(this,DetailViewWeather::class.java)
            intent.putExtra("id",it.woeid)
            startActivity(intent)

            
        }
        mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, "$query", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = weatherApi.searchCities(text)
                    if(response.isSuccessful){
                        withContext(Dispatchers.Main) {
                            citiesAdapter.addCities(response.body()!!)
                        }
                    }

                }
                return false
            }
        })

        citiesRecyclerView.layoutManager = LinearLayoutManager(this)
        citiesRecyclerView.setHasFixedSize(true)

        citiesRecyclerView.adapter = citiesAdapter









    }
}
