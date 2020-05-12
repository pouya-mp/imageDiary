package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.databinding.FragmentInputTimeForStopwatchBinding
import com.pouyaa.imagediary.viewmodel.InputTimeForStopwatchViewModel

/**
 * A simple [Fragment] subclass.
 */
class InputTimeForStopwatchFragment : Fragment() {

    private var _binding: FragmentInputTimeForStopwatchBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: InputTimeForStopwatchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInputTimeForStopwatchBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.stopwatchTimeInput.addTextChangedListener {
            viewModel.changeDetected(binding.stopwatchTimeInput.text.toString())

        }


        binding.startStopwatch.setOnClickListener {

            viewModel.start()?.let {
                findNavController().navigate(it)
            }

        }
    }


}
