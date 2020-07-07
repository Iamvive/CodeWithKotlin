package com.appwork.codewithkotlin.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appwork.codewithkotlin.AppNavigator
import com.appwork.codewithkotlin.R
import kotlinx.android.synthetic.main.fragment_location_entry.view.*

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {
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
            R.layout.fragment_location_entry,
            container,
            false
        )
        view.btnZip.setOnClickListener {
            val strZip = view.edtZipCode.text.toString().trim()
            if (strZip.length >= 5) {
                navigator.navigateToCurrentForecast(strZip)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Enter valid zipcode",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return view
    }
}