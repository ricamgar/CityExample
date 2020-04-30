package com.utad.networking.ui.citydetail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utad.networking.R
import com.utad.networking.data.remote.RetrofitFactory
import com.utad.networking.data.remote.RemoteRepository
import com.utad.networking.data.remote.RetrofitRemoteRepository
import com.utad.networking.model.WeatherDetail

class CityDetailActivity : AppCompatActivity(), CityDetailView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        val cityId = intent.extras?.getInt("city_id")

        val remoteRepository: RemoteRepository =
            RetrofitRemoteRepository(RetrofitFactory.getWeatherApi())
        val presenter = CityDetailPresenter(this, remoteRepository)
        presenter.fetchCityDetail(cityId!!)
    }

    override fun showWeatherDetail(detail: List<WeatherDetail>) {
        // show details in a list
        Log.e("DETAIL", detail.toString())
    }

    override fun showError() {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
    }
}
