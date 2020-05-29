package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.pouyaa.imagediary.DataBaseHandler
import com.pouyaa.imagediary.PlaceAdapter
import com.pouyaa.imagediary.R
import com.pouyaa.imagediary.databinding.FragmentPlacesBinding
import com.pouyaa.imagediary.model.PlaceModel
import com.pouyaa.imagediary.viewmodel.PlacesViewModel

class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private val viewModel: PlacesViewModel by viewModels()
    private val adapter = PlaceAdapter() // this is for second adapter

    // This property is only valid between onCreateView and onDestroyView.
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
            findNavController().navigate(PlacesFragmentDirections.actionPlacesFragmentToAddPlacesFragment())
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
//         second adapter
        binding.placesListRecycleView.adapter = adapter
        adapter.submitList(myPlacesList)
        adapter.setOnClickListener(object : PlaceAdapter.OnClickListener {
            override fun onClick(position: Int, model: PlaceModel) {
                findNavController().navigate(
                    PlacesFragmentDirections.actionPlacesFragmentToPlaceDetailFragment(
                        model
                    )
                )
            }
        })
//         end of second adapter


//        context?.let {
//            val placeAdapter =
//                PlacesAdapter(it, myPlacesList)
//            binding.placesListRecycleView.adapter = placeAdapter
//            placeAdapter.setOnClickListener(object :
//                PlacesAdapter.OnClickListener {
//                override fun onClick(position: Int, model: PlaceModel) {
//                    findNavController().navigate(
//                        PlacesFragmentDirections.actionPlacesFragmentToPlaceDetailFragment(
//                            model
//                        )
//                    )
//                }
//
//            })
//
//        }
//        context?.let {
//            val editSwipeHandler = object : SwipeToEditCallback(it) {
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val adapter = binding.placesListRecycleView.adapter as PlacesAdapter
//                    val model = adapter.getPlaceModel(viewHolder.adapterPosition)
//                    val action = PlacesFragmentDirections.actionPlacesFragmentToAddPlacesFragment()
//                    action.place = model
//                    findNavController().navigate(action)
//                }
//
//            }
//            val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
//            editItemTouchHelper.attachToRecyclerView(binding.placesListRecycleView)
//
//            val deleteSwipeHandler = object : SwipeToDeleteCallback(it) {
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val adapter = binding.placesListRecycleView.adapter as PlacesAdapter
//                    val model = adapter.getPlaceModel(viewHolder.adapterPosition)
//                    val dbHandler = DataBaseHandler(it)
//                    dbHandler.removeMyPlace(model)
//                    fetchPlacesList()
//                }
//
//            }
//            val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
//            deleteItemTouchHelper.attachToRecyclerView(binding.placesListRecycleView)
//
//        }
    }


}
