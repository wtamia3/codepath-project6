package com.example.bitfitp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var bitFitDatabase: BitFitDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        bitFitDatabase = BitFitDatabase.getDatabase(requireContext())

        displaySummary(view)
        return view
    }

    private fun displaySummary(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            val entryCount = bitFitDatabase.bitFitDao().getAllEntries().size
            activity?.runOnUiThread {
                view.findViewById<TextView>(R.id.entryCountTextView).text =
                    "Total Entries: $entryCount"
            }
        }
    }
}
