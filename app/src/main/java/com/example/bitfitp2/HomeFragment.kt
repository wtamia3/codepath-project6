package com.example.bitfitp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var bitFitDatabase: BitFitDatabase
    private lateinit var bitFitAdapter: BitFitAdapter
    private val entries = mutableListOf<BitFitEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        bitFitDatabase = BitFitDatabase.getDatabase(requireContext())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        bitFitAdapter = BitFitAdapter(entries)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = bitFitAdapter

        loadEntries()
        return view
    }

    private fun loadEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            entries.clear()
            entries.addAll(bitFitDatabase.bitFitDao().getAllEntries())
            activity?.runOnUiThread { bitFitAdapter.notifyDataSetChanged() }
        }
    }
}
