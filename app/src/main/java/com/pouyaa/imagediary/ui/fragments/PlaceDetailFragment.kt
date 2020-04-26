package com.pouyaa.imagediary.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
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
    private lateinit var shareContent: String
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
        shareContent = "${args.place.date} \n${args.place.title} \n${args.place.description}"

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.place_details_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.placeShareMenuButton -> shareContent?.let {

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND_MULTIPLE
                    putExtra(Intent.EXTRA_TEXT, it)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Share to"))
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
