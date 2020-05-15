package com.pouyaa.imagediary.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.R
import com.pouyaa.imagediary.databinding.FragmentPlaceDetailBinding
import com.pouyaa.imagediary.model.PlaceModel
import timber.log.Timber

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
        Timber.i("onCreateOptionsMenu()")
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
        Timber.i("onDestroyView()")
        super.onDestroyView()
        _binding = null
    }


    override fun onDestroyOptionsMenu() {
        Timber.i("onDestroyOptionsMenu()")
        super.onDestroyOptionsMenu()
    }


    override fun onAttachFragment(childFragment: Fragment) {
        Timber.i("onAttachFragment()")
        super.onAttachFragment(childFragment)
    }

    override fun onAttach(context: Context) {
        Timber.i("onAttach(context)")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.i("onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        _binding = null
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.i("onDetach()")
        super.onDetach()
    }

}
