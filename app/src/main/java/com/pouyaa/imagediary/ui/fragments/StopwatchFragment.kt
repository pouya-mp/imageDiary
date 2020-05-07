package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pouyaa.imagediary.databinding.FragmentStopwatchBinding
import com.pouyaa.imagediary.viewmodel.StopwatchViewModel

/**
 * A simple [Fragment] subclass.
 */
class StopwatchFragment : Fragment() {

    private var _binding: FragmentStopwatchBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: StopwatchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.secondsPassed.observe(viewLifecycleOwner, Observer {
            binding.timeStopwatch.text = "${it ?: 0}"
        })
        
        binding.startStopwatch.setOnClickListener { viewModel.didClickStartButton() }
        binding.stopStopwatch.setOnClickListener { viewModel.didClickStopButton() }

    }

}
