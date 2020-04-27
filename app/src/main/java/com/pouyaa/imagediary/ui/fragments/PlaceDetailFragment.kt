package com.pouyaa.imagediary.ui.fragments

import android.content.Intent
import android.mtp.MtpConstants
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.R
import com.pouyaa.imagediary.databinding.FragmentPlaceDetailBinding
import com.pouyaa.imagediary.model.PlaceModel

/**
 * A simple [Fragment] subclass.
 */
class PlaceDetailFragment : Fragment() {

    private var _binding: FragmentPlaceDetailBinding? = null
    private lateinit var editPlaceModel: PlaceModel

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = PlaceDetailFragmentArgs.fromBundle(requireArguments())
        binding.place = args.place
        binding.invalidateAll()
        editPlaceModel = args.place
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.place_details_menu, menu)

        if (getShareIntent()?.resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.placeShareMenuButton)?.isVisible = false
        }
    }

    private fun getShareIntent(): Intent? {
        if (activity == null) {
            return null
        }
        return arguments?.let {
            val args = PlaceDetailFragmentArgs.fromBundle(it)
            ShareCompat.IntentBuilder.from(requireActivity())
                .setText("${args.place.date} \n${args.place.title} \n${args.place.description}")
                .setType("text/plain")
                .intent
        } ?: run {
            null
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.placeShareMenuButton -> {
                startActivity(getShareIntent())
            }

            R.id.placeEditMenuButton -> {
                val action =
                    PlaceDetailFragmentDirections.actionPlaceDesctiptionFragmentToAddPlacesFragment()

                action.place = editPlaceModel
                findNavController().navigate(action)
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
