package com.utad.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utad.networking.model.City

class CitiesAdapter(private val listener: (City) -> Unit): RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private var cities = listOf<City>()


    fun addCities(newCities: List<City>) {
        this.cities = newCities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position], listener)
    }

    class ViewHolder private constructor(view: View): RecyclerView.ViewHolder(view) {
        private val cityTxt = view.findViewById<TextView>(R.id.cityTxt)

        fun bind(city: City, listener: (City) -> Unit) {
            cityTxt.text = city.title
            this.itemView.setOnClickListener { listener.invoke(city) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

}