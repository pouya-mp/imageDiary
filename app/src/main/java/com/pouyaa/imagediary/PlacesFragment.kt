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
            findNavController().navigate(R.id.action_placesFragment_to_addPlacesFragment)
        }
        fetchPlacesList()

    }

    private fun fetchPlacesList() {

        context?.let {
            val dbHandler = DataBaseHandler(it)
            val placesList = dbHandler.myPlacesList()

            if (placesList.isNotEmpty()) {
                placesListRecycleView.visibility = View.VISIBLE
                noPlacesAddedYetTextView.visibility = View.GONE
                setupMyPlacesRecycleView(placesList)
            } else {
                placesListRecycleView.visibility = View.GONE
                noPlacesAddedYetTextView.visibility = View.VISIBLE

            }
        }


    }

    private fun setupMyPlacesRecycleView(myPlacesList: List<PlaceModel>) {
        placesListRecycleView.layoutManager = LinearLayoutManager(context)
        placesListRecycleView.setHasFixedSize(true)

        context?.let {
            val placeAdapter = PlacesAdapter(it, myPlacesList)
            placesListRecycleView.adapter = placeAdapter
            placeAdapter.setOnClickListener(object : PlacesAdapter.OnClickListener{
                override fun onClick(position: Int, model: PlaceModel) {
                    val bundle = Bundle()
                    bundle.putSerializable(DETAILS_FRAGMENT_KEY,model)
                    findNavController().navigate(R.id.action_placesFragment_to_placeDetailFragment,bundle)
                }

            })


        }


    }

    companion object{
        const val DETAILS_FRAGMENT_KEY = "DetailsFragmentKey"
    }


}
