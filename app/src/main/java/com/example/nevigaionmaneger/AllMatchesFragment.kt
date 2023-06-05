package com.example.nevigaionmaneger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.nevigaionmaneger.databinding.FragmentAllMatchesBinding

class AllMatchesFragment : Fragment() {

    private lateinit var requestQueue: RequestQueue

    private lateinit var binding: FragmentAllMatchesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAllMatchesBinding.inflate(layoutInflater)

        initRequestQueue()

        initData()

        return binding.root

    }

    private fun initRequestQueue() {
        requestQueue = Volley.newRequestQueue(requireContext())
    }


    private fun initData() {

        val req = JsonObjectRequest(
            Request.Method.GET,
            "https://api.foursquare.com/v2/venues/search?ll=40.7484,-73.9857&oauth_token=NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ&v=20180616",
            null,
                        { jsonObject ->
                val responseObj = jsonObject.getJSONObject("response")
                val venuesArray = responseObj.getJSONArray("venues")

                val modelList = ArrayList<Model>()

                for (i in 0 until venuesArray.length()) {
                    val venueObj = venuesArray.getJSONObject(i)
                    val id = venueObj.getString("id")
                    val location = venueObj.getJSONObject("location")

                    val city = location.getString("city")

                    val state = location.getString("state")

                    val country = location.getString("country")

                    val model = Model(id, city,state,country)
                    modelList.add(model)
                }

                // Now you have a list of Venue objects with 'id' and 'name' extracted from the API response

                binding.rvVenueMatchList.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,false)
                binding.rvVenueMatchList.adapter = MatchListAdapter(modelList,0)


            },
            { error ->
                Log.e("tag", "error")
            }
        )
        requestQueue.add(req)

    }
}