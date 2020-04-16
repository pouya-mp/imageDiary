package com.pouyaa.imagediary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_places.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddPlacesFragment : Fragment() {

    private var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addDateEditText.setOnClickListener { getActivity()?.let { it1 ->
            PickDate(addDateEditText).selectDate(
                it1
            )
        } }

    }



}
