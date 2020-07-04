package com.appwork.codewithkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DailyForecastVH(view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(data: ForecastData) {
        itemView.txtTitle.text = String.format("%.2f", data.temp.toString())
        itemView.subTitle.text = data.description
    }
}

class DailyForecastAdapter(private val clickHandler: (ForecastData) -> Unit) :
    ListAdapter<ForecastData, DailyForecastVH>(
        DIFF_CONFIG
    ) {
    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<ForecastData>() {
            override fun areItemsTheSame(
                oldItem: ForecastData,
                newItem: ForecastData
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: ForecastData,
                newItem: ForecastData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyForecastVH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastVH(itemView)
    }

    override fun onBindViewHolder(holder: DailyForecastVH, position: Int) {
        holder.bindData(getItem(holder.adapterPosition))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }
}