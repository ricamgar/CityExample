package com.utad.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utad.networking.data.Day
import com.utad.networking.model.City
import kotlinx.android.synthetic.main.days_item.view.*

class DayAdapter():  RecyclerView.Adapter<DayAdapter.ViewHolder>()  {
    private var days = listOf<Day>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
       return days.size
    }

    override fun onBindViewHolder(holder: DayAdapter.ViewHolder, position: Int) {
        holder.bind(days[position])
    }
    fun addDays(newDays :List<Day>){
        this.days = newDays
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(view: View): RecyclerView.ViewHolder(view) {
        private val minTemp = view.tempsmin
        private val maxTemp = view.tempsmax
        private val temp = view.temepratureItem
        private val state = view.stateItem
        private val predicable = view.predictItem
        private val date = view.dateItem

        fun bind(day: Day) {
            minTemp.text = day.min_temp.toString()
            maxTemp.text = day.max_temp.toString()
            temp.text = day.the_temp.toString()
            state.text = day.wheater_state_name
            predicable.text = day.predicability.toString()
            date.text = day.applicable_date

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.days_item, parent, false)
                return ViewHolder(view)
            }
        }
    }
}