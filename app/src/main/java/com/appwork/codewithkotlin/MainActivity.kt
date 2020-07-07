package com.appwork.codewithkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appwork.codewithkotlin.forecast.CurrentForecastFragment
import com.appwork.codewithkotlin.location.LocationEntryFragment

class MainActivity : AppCompatActivity(), AppNavigator {
    private val forecastRepo = ForecastRepo()
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tempDisplaySettingsManager = TempDisplaySettingsManager(this)
        supportFragmentManager.beginTransaction()
            .add(R.id.containerFragment, LocationEntryFragment())
            .commit()
    }

    override fun navigateToCurrentForecast(zipCode: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment,CurrentForecastFragment.newInstance(zipCode))
            .commit()
    }

    override fun moveToLocationEntry() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, LocationEntryFragment())
            .commit()
    }
}