package com.pouyaa.imagediary.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pouyaa.imagediary.model.PlaceModel
import com.pouyaa.imagediary.R
import kotlinx.android.synthetic.main.fragment_place_detail.*

/**
 * A simple [Fragment] subclass.
 */
class PlaceDetailFragment : Fragment() {

    private var title: String = ""
    private var placeImage: String = ""
    private var placeDescription: String = "123"
    private var placeLocation: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_place_detail, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (arguments?.getSerializable(PlacesFragment.DETAILS_FRAGMENT_KEY) as? PlaceModel)?.let {
            refreshView(it)
        }
    }


    private fun refreshView(model: PlaceModel) {
        titleOfPlaceDetailFragment.text = model.title
        descriptionOfPlaceDetailsFragment.text = model.description
        imageOfPlaceDetailsFragment.setImageURI(Uri.parse(model.image))
        locationOfPlaceDetailsFragment.text = model.location
    }


}
