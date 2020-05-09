package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.databinding.FragmentCountdownTimerBinding
import com.pouyaa.imagediary.viewmodel.CountdownViewModel
import com.pouyaa.imagediary.viewmodelfactory.CountdownViewModelFactory

class CountdownTimerFragment : Fragment() {

    private var _binding: FragmentCountdownTimerBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var viewModel: CountdownViewModel
    private lateinit var viewModelFactory: CountdownViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountdownTimerBinding.inflate(inflater, container, false)

        viewModelFactory = CountdownViewModelFactory(150)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(CountdownViewModel::class.java)

        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.remainingTime.observe(viewLifecycleOwner, Observer {
            binding.textView.text = "${it ?: 0}"
        })

        viewModel.countDownTimerDidFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.onCountdownTimerFinishCompleted()
                findNavController().popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
