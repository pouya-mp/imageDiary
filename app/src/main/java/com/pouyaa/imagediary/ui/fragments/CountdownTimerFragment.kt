package com.pouyaa.imagediary.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pouyaa.imagediary.databinding.FragmentCountdownTimerBinding
import com.pouyaa.imagediary.utils.CustomCountdown
import com.pouyaa.imagediary.viewmodel.CountdownViewModel
import timber.log.Timber

class CountdownTimerFragment : Fragment() {

    private var _binding: FragmentCountdownTimerBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: CountdownViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountdownTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.remainingTime.observe(viewLifecycleOwner, Observer {
            binding.textView.text = "${it ?: 0}"
        })

        binding.startCountdown.setOnClickListener {
            viewModel.didClickStartButton()
        }

        binding.stopCountdown.setOnClickListener {
            viewModel.didClickStopButton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
