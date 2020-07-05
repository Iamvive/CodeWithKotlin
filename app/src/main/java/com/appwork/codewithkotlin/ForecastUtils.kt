package com.appwork.codewithkotlin

fun formatTempForDisplay(
    temp: Float,
    tempDisplaySettings: TempDisplaySettings
): String {
    return when (tempDisplaySettings) {
        TempDisplaySettings.Fahrenheit ->
            String.format("%.2f F", temp)
        TempDisplaySettings.Celsius -> {
            val temperature = (temp - 32f) * (5f / 9f)
            String.format("%.2f C", temperature)
        }
    }
}