package com.pouyaa.imagediary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pouyaa.imagediary.databinding.FragmentCountdownTimerBinding
import com.pouyaa.imagediary.utils.CustomCountdown

class CountdownTimer : Fragment() {

    private var _binding: FragmentCountdownTimerBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountdownTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startCountdown.setOnClickListener {
            startCountdown()
        }

        binding.stopCountdown.setOnClickListener { stopCountdown() }


    }

    private fun startCountdown() {
        if (binding.countDownTimerTimeInput.text.toString() == "") {
            Toast.makeText(context, "Enter number", Toast.LENGTH_SHORT).show()
        } else {
            binding.countDownTimerTimeInput.text?.let {
                it.toString()?.let { it1 ->
                    val cnt = CustomCountdown()
                    cnt.startCountdownTimer(it1.toInt(), binding.countDownTimerTimeInput)

                }

            }
        }
    }

    private fun stopCountdown() {
        val cnt = CustomCountdown()
        cnt.stopCountdownTimer(binding.countDownTimerTimeInput)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
