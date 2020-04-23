package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pouyaa.imagediary.databinding.FragmentPlaceDetailBinding
import com.pouyaa.imagediary.model.PlaceModel

/**
 * A simple [Fragment] subclass.
 */
class PlaceDetailFragment : Fragment() {

    private var _binding: FragmentPlaceDetailBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (arguments?.getSerializable(PlacesFragment.DETAILS_FRAGMENT_KEY) as? PlaceModel)?.let {
            binding.place = it
            binding.invalidateAll()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
