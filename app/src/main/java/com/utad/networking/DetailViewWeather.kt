package com.utad.networking

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.data.RetrofitFactory
import kotlinx.android.synthetic.main.content_detail_view_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailViewWeather : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_detail_view_weather)
        val weatherApi = RetrofitFactory.getWeatherApi()
        val id =intent.getIntExtra("id",0)
        Log.e("this",id.toString())
        val daysAdapter = DayAdapter()
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.searchCity(id)
            if(response.isSuccessful){
                withContext(Dispatchers.Main) {
                    daysAdapter.addDays(response.body()!!)
                }
            }

        }
        detailView.layoutManager = LinearLayoutManager(this)
        detailView.setHasFixedSize(true)

        detailView.adapter = daysAdapter


    }
}
