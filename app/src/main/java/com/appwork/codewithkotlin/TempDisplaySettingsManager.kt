package com.appwork.codewithkotlin

import android.content.Context

enum class TempDisplaySettings {
    Fahrenheit, Celsius
}

class TempDisplaySettingsManager(context: Context) {
    private val prefs =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSetting(setting: TempDisplaySettings) {
        prefs.edit().putString("key_temp_display", setting.name).apply()
    }

    fun getDisplaySetting(): TempDisplaySettings {
        val settingValue = prefs.getString(
            "key_temp_display",
            TempDisplaySettings.Fahrenheit.name
        ) ?: TempDisplaySettings.Fahrenheit.name
        return TempDisplaySettings.valueOf(settingValue)
    }
}