package com.pouyaa.imagediary.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    private companion object {
        private const val TAG = "placeDetailFragment"
    }

    private var _binding: FragmentPlaceDetailBinding? = null
    private lateinit var editPlaceModel: PlaceModel
    private val sharedContent: String by lazy {
        val args = PlaceDetailFragmentArgs.fromBundle(requireArguments())
        "${args.place.date} \n${args.place.title} \n${args.place.description}"
    }

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
        Log.i(TAG, "onCreateOptionsMenu()")
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
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(sharedContent)
            .setType("text/plain")
            .intent
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
        Log.i(TAG, "onDestroyView()")
        super.onDestroyView()
        _binding = null
    }


    override fun onDestroyOptionsMenu() {
        Log.i(TAG, "onDestroyOptionsMenu()")
        super.onDestroyOptionsMenu()
    }


    override fun onAttachFragment(childFragment: Fragment) {
        Log.i(TAG, "onAttachFragment()")
        super.onAttachFragment(childFragment)
    }

    override fun onAttach(context: Context) {
        Log.i(TAG, "onAttach(context)")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.i(TAG, "onStart()")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, "onPause()")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        _binding = null
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i(TAG, "onDetach()")
        super.onDetach()
    }

}
