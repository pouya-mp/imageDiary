package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pouyaa.imagediary.*
import com.pouyaa.imagediary.databinding.FragmentPlacesBinding
import com.pouyaa.imagediary.model.PlaceModel

/**
 * A simple [Fragment] subclass.
 */
class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPlacesFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_placesFragment_to_addPlacesFragment)
        }

        fetchPlacesList()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.places_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchPlacesList() {

        context?.let {
            val dbHandler = DataBaseHandler(it)
            val placesList = dbHandler.myPlacesList()

            if (placesList.isNotEmpty()) {
                binding.placesListRecycleView.visibility = View.VISIBLE
                binding.noPlacesAddedYetTextView.visibility = View.GONE
                setupMyPlacesRecycleView(placesList)
            } else {
                binding.placesListRecycleView.visibility = View.GONE
                binding.noPlacesAddedYetTextView.visibility = View.VISIBLE

            }
        }


    }

    private fun setupMyPlacesRecycleView(myPlacesList: List<PlaceModel>) {
        binding.placesListRecycleView.layoutManager = LinearLayoutManager(context)
        binding.placesListRecycleView.setHasFixedSize(true)

        context?.let {
            val placeAdapter =
                PlacesAdapter(it, myPlacesList)
            binding.placesListRecycleView.adapter = placeAdapter
            placeAdapter.setOnClickListener(object :
                PlacesAdapter.OnClickListener {
                override fun onClick(position: Int, model: PlaceModel) {
                    val bundle = Bundle()
                    bundle.putSerializable(DETAILS_FRAGMENT_KEY, model)
                    findNavController().navigate(
                        R.id.action_placesFragment_to_placeDetailFragment,
                        bundle
                    )
                }

            })


        }
        context?.let {
            val editSwipeHandler = object : SwipeToEditCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = binding.placesListRecycleView.adapter as PlacesAdapter
                    val model = adapter.getPlaceModel(viewHolder.adapterPosition)
                    val bundle = Bundle()
                    bundle.putSerializable(DETAILS_FRAGMENT_KEY, model)
                    findNavController().navigate(
                        R.id.action_placesFragment_to_addPlacesFragment,
                        bundle
                    )
                }

            }
            val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
            editItemTouchHelper.attachToRecyclerView(binding.placesListRecycleView)

            val deleteSwipeHandler = object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = binding.placesListRecycleView.adapter as PlacesAdapter
                    val model = adapter.getPlaceModel(viewHolder.adapterPosition)
                    val dbHandler = DataBaseHandler(it)
                    dbHandler.removeMyPlace(model)
                    fetchPlacesList()
                }

            }
            val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
            deleteItemTouchHelper.attachToRecyclerView(binding.placesListRecycleView)

        }


    }


    companion object {
        const val DETAILS_FRAGMENT_KEY = "DetailsFragmentKey"
    }


}
