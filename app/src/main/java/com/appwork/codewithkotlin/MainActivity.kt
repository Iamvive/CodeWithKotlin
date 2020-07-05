package com.appwork.codewithkotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.appwork.codewithkotlin.details.ForecastDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val forecastRepo = ForecastRepo()
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager


    private fun sendForecast(forecast: ForecastData) {
        val intent = Intent(this, ForecastDetailsActivity::class.java)
        intent.putExtra("temp_key", forecast.temp)
        intent.putExtra("temp_desc", forecast.description)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tempDisplaySettingsManager = TempDisplaySettingsManager(this)
        setRecyclerView()
        btnZip.setOnClickListener {
            val strZip = edtZipCode.text.toString().trim()
            if (strZip.length >= 5) {
                forecastRepo.loadForecastData(strZip)
                listForecast.layoutManager =
                    LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                val dailyForecastAdapter =
                    DailyForecastAdapter(tempDisplaySettingsManager) { forecast ->
                        sendForecast(forecast)
                    }
                listForecast.adapter = dailyForecastAdapter
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

    }
}