package com.appwork.codewithkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val forecastRepo = ForecastRepo()
    private val dailyForecastAdapter = DailyForecastAdapter() { forecast ->
        Toast.makeText(
            this,
            getString(
                R.string.forecast_data,
                forecast.temp,
                forecast.description
            ),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        btnZip.setOnClickListener {
            val strZip = edtZipCode.text.toString().trim()
            if (strZip.length >= 5) {
                forecastRepo.loadForecastData(strZip)
                val weeklyForecastObserver =
                    Observer<List<ForecastData>> { forecastItems ->//update list adapter
                        dailyForecastAdapter.submitList(forecastItems)
                    }
                forecastRepo.weeklyForecast.observe(
                    this,
                    weeklyForecastObserver
                )
            }
        }
    }

    private fun setRecyclerView() {
        listForecast.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listForecast.adapter = dailyForecastAdapter
    }
}