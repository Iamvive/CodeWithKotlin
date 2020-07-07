package com.appwork.codewithkotlin.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.appwork.codewithkotlin.*
import com.appwork.codewithkotlin.details.ForecastDetailsActivity
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast.view.*
import kotlinx.android.synthetic.main.fragment_current_forecast.view.fab

class CurrentForecastFragment : Fragment() {
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager
    private val forecastRepo = ForecastRepo()
    private lateinit var navigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_current_forecast,
            container,
            false
        )
        val zipCode = arguments?.getString(KEY_ZIP_CODE) ?: ""
        tempDisplaySettingsManager =
            TempDisplaySettingsManager(requireContext())
        view.listForecast.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        val dailyForecastAdapter =
            DailyForecastAdapter(tempDisplaySettingsManager) { forecast ->
                sendForecast(forecast)
            }
        view.listForecast.adapter = dailyForecastAdapter
        val weeklyForecastObserver =
            Observer<List<ForecastData>> { forecastItems ->//update list adapter
                dailyForecastAdapter.submitList(forecastItems)
            }
        forecastRepo.weeklyForecast.observe(
            this,
            weeklyForecastObserver
        )
        forecastRepo.loadForecastData(zipCode)
        view.fab.setOnClickListener {
            navigator.moveToLocationEntry()
        }
        return view
    }

    private fun sendForecast(forecast: ForecastData) {
        val intent =
            Intent(requireContext(), ForecastDetailsActivity::class.java)
        intent.putExtra("temp_key", forecast.temp)
        intent.putExtra("temp_desc", forecast.description)
        startActivity(intent)
    }

    companion object {
        const val KEY_ZIP_CODE = "key_zip_code"
        fun newInstance(zipCode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()
            val argrs = Bundle()
            argrs.putString(KEY_ZIP_CODE, zipCode)
            fragment.arguments = argrs
            return fragment
        }
    }
}