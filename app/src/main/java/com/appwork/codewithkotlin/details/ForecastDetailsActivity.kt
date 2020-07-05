package com.appwork.codewithkotlin.details

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.appwork.codewithkotlin.R
import com.appwork.codewithkotlin.TempDisplaySettings
import com.appwork.codewithkotlin.TempDisplaySettingsManager
import com.appwork.codewithkotlin.formatTempForDisplay
import kotlinx.android.synthetic.main.activity_forecast_details.*

class ForecastDetailsActivity : AppCompatActivity() {
    private lateinit var tempDisplaySettings: TempDisplaySettingsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)
        tempDisplaySettings = TempDisplaySettingsManager(this)
        setTitle(R.string.forecast_details)
        if (intent != null) {
            if (intent.hasExtra("temp_key")) {
                val tempValue = intent.getFloatExtra("temp_key", 0f)
                tvTemp.text = formatTempForDisplay(
                    tempValue,
                    tempDisplaySettings.getDisplaySetting()
                )
            }

            if (intent.hasExtra("temp_desc"))
                tvTempDesc.text = intent.getStringExtra("temp_desc")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_display_unit -> {
                if (!isFinishing) {
                    showDialog()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.setting_title))
            .setMessage(getString(R.string.setting_msg))
            .setPositiveButton(
                getString(R.string.setting_pos_button)
            ) { _, _ ->
                tempDisplaySettings.updateSetting(TempDisplaySettings.Fahrenheit)
            }
            .setNeutralButton(getString(R.string.setting_neg_button)) { _, _ ->
                tempDisplaySettings.updateSetting(TempDisplaySettings.Celsius)
            }
            .setOnDismissListener {
                Toast.makeText(
                    this,
                    "Settings will take affect on restart app.",
                    Toast.LENGTH_LONG
                ).show()
            }
        builder.show()
    }
}