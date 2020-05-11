package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.databinding.FragmentStopwatchBinding
import com.pouyaa.imagediary.viewmodel.StopwatchViewModel
import com.pouyaa.imagediary.viewmodelfactory.StopwatchViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
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
        viewModelFactory = StopwatchViewModelFactory(args.time)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(StopwatchViewModel::class.java)

        binding.vm = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.stopStopwatch.setOnClickListener {
            viewModel.didClickStopButton()
            findNavController().popBackStack()
        }

    }

}
