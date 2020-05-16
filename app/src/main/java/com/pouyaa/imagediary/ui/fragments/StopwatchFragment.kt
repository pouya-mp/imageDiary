package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.databinding.FragmentStopwatchBinding
import com.pouyaa.imagediary.ui.ImageDiaryApp
import com.pouyaa.imagediary.viewmodel.StopwatchViewModel
import com.pouyaa.imagediary.viewmodelfactory.StopwatchViewModelFactory

class StopwatchFragment : Fragment() {

    private var _binding: FragmentStopwatchBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: StopwatchViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)

        val args = StopwatchFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = StopwatchViewModelFactory(args.time, ImageDiaryApp.instance)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(StopwatchViewModel::class.java)

        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            if (it?.isBlank() == false) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.didFinishShowingToast()
            }
        })


        viewModel.shouldPopView.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
            }
        })
    }

}
