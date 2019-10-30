package com.utad.networking.ui.citydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.utad.networking.R
import com.utad.networking.data.RetrofitFactory
import com.utad.networking.model.WeatherDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityDetailActivity : AppCompatActivity(), CityDetailView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        val cityId = intent.extras?.getInt("city_id")

        val presenter = CityDetailPresenter(this)
        presenter.fetchCityDetail(cityId!!)
    }

    override fun showWeatherDetail(detail: List<WeatherDetail>) {

    }
}
