package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.databinding.FragmentInputTimeForStopwatchBinding

/**
 * A simple [Fragment] subclass.
 */
class InputTimeForStopwatchFragment : Fragment() {

    private var _binding: FragmentInputTimeForStopwatchBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInputTimeForStopwatchBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startStopwatch.setOnClickListener {

            if (binding.stopwatchTimeInput.text.toString() != "") {
                val seconds = binding.stopwatchTimeInput.text.toString().toInt()
                if (seconds > 0) {
                    val action =
                        InputTimeForStopwatchFragmentDirections.actionInputTimeForStopwatchFragmentToStopwatchFragment()
                    action.time = seconds
                    findNavController().navigate(action)
                }
            } else {
                Toast.makeText(context, "Enter a time in seconds", Toast.LENGTH_SHORT).show()
            }


        }
    }


}
