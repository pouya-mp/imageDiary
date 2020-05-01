package com.pouyaa.imagediary.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pouyaa.imagediary.databinding.FragmentCountdownTimerBinding
import com.pouyaa.imagediary.utils.CustomCountdown

class CountdownTimer : Fragment() {

    private companion object {
        private var TAG = "countdownTimerFragment"
    }

    private var _binding: FragmentCountdownTimerBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView()")
        _binding = FragmentCountdownTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated()")
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.i(TAG, "onCreateOptionsMenu()")
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyOptionsMenu() {
        Log.i(TAG, "onDestroyOptionsMenu()")
        super.onDestroyOptionsMenu()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        Log.i(TAG, "onAttachFragment()")
        super.onAttachFragment(childFragment)
    }

    override fun onAttach(context: Context) {
        Log.i(TAG, "onAttach(context)")
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity) {
        Log.i(TAG, "onAttach(activity)")
        super.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.i(TAG, "onStart()")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, "onPause()")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        _binding = null
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i(TAG, "onDetach()")
        super.onDetach()
    }

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView()")
        super.onDestroyView()
        _binding = null
    }
}
