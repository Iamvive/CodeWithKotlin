package com.appwork.codewithkotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepo {
    private val _weeklyForecast = MutableLiveData<List<ForecastData>>()
    val weeklyForecast: LiveData<List<ForecastData>> = _weeklyForecast

    fun loadForecastData(zipcode: String) {
        val randomValues = List(7) {
            Random.nextFloat().rem(100) * 100
        }
        val forecastItems = randomValues.map { randomValues ->
            ForecastData(
                randomValues,
                getTempDescription(randomValues)
            )
        }
        _weeklyForecast.value = forecastItems
    }

    private fun getTempDescription(temp: Float): String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "It doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than i prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's better spot"
            in 80f.rangeTo(90f) -> "Getting warm"
            in 90f.rangeTo(100f) -> "Where is the A/C"
            in 100f.rangeTo(Float.MAX_VALUE) -> "Global warming"
            else -> "Doesn't compute"
        }
    }
}