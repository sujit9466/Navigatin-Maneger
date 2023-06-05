package com.example.nevigaionmaneger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nevigaionmaneger.databinding.FragmentSavedMatchesBinding

class SavedMatchesFragment : Fragment() {

    private lateinit var binding: FragmentSavedMatchesBinding

    private lateinit var venueMatchListAdapter: MatchListAdapter

    private lateinit var modelList: ArrayList<Model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedMatchesBinding.inflate(layoutInflater)

        val databaseHelper = DatabaseHelper(requireContext())

        modelList = databaseHelper.getAllModel()

        databaseHelper.close()
        binding.rvSavedVenueMatchList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )



        for (model in modelList) {
            Log.d("Venue", "ID: ${model.id}")
            Log.d("Venue", "City: ${model.city}")
            Log.d("Venue", "State: ${model.state}")
            Log.d("Venue", "Country: ${model.country}")

        }
        venueMatchListAdapter = MatchListAdapter(modelList, 1)
        binding.rvSavedVenueMatchList.adapter = venueMatchListAdapter

        venueMatchListAdapter.setOnClickListener = SetOnSavedMatchClickListener()


        return binding.root
    }

    inner class SetOnSavedMatchClickListener : MatchListAdapter.SetOnClickListener {
        override fun setOnClick(position: Int) {
            modelList.removeAt(position)
            venueMatchListAdapter.notifyDataSetChanged()


        }

    }

}