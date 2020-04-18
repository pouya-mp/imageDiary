package com.pouyaa.imagediary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_places.*

/**
 * A simple [Fragment] subclass.
 */
class PlacesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPlacesFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.addPlacesFragment)
        }
        getPlacesList()

    }

    private fun getPlacesList(){
        val dbHandler = activity?.let { DataBaseHandler(it) }
        val placesList = dbHandler?.getMyPlacesList()

        if (placesList != null) {
            if (placesList.size > 0){
                placesListRecycleView.visibility = View.VISIBLE
                noPlacesAddedYetTextView.visibility = View.GONE
                if (placesList != null) {
                    setupMyPlacesRecycleView(placesList)
                }
            }else {
                placesListRecycleView.visibility = View.GONE
                noPlacesAddedYetTextView.visibility = View.VISIBLE

            }
        }

    }

    private fun setupMyPlacesRecycleView(myPlacesList: ArrayList<PlaceModel>){
        placesListRecycleView.layoutManager = LinearLayoutManager(activity)
        placesListRecycleView.setHasFixedSize(true)

        val placesAdapter = activity?.let { PlacesAdapter(it,myPlacesList) }
        placesListRecycleView.adapter = placesAdapter
    }

}
