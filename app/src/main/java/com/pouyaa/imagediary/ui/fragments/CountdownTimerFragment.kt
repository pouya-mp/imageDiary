package com.pouyaa.imagediary.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.pouyaa.imagediary.databinding.FragmentCountdownTimerBinding
import com.pouyaa.imagediary.utils.CustomCountdown
import timber.log.Timber

class CountdownTimerFragment : Fragment() {

    private var countdown = CustomCountdown()

    private companion object {
        private const val SAVED_PASSED_SECONDS = "SAVED_PASSED_SECONDS"
    }

    private var _binding: FragmentCountdownTimerBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView()")
        _binding = FragmentCountdownTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            countdown.countDownTime = it.getInt(SAVED_PASSED_SECONDS, 10)
        }
        Timber.i("onViewCreated()")
        binding.startCountdown.setOnClickListener {
            startCountdown()
        }

        binding.stopCountdown.setOnClickListener { stopCountdown() }


    }

    private fun startCountdown() {
        countdown.startCountdownTimer()

    }

    private fun stopCountdown() {
        countdown.stopCountdownTimer()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Timber.i("onCreateOptionsMenu()")
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyOptionsMenu() {
        Timber.i("onDestroyOptionsMenu()")
        super.onDestroyOptionsMenu()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        Timber.i("onAttachFragment()")
        super.onAttachFragment(childFragment)
    }

    override fun onAttach(context: Context) {
        Timber.i("onAttach(context)")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.i("onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_PASSED_SECONDS, countdown.countDownTime)
        Timber.i("onSaveInstanceState()")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        _binding = null
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.i("onDetach()")
        super.onDetach()
    }

    override fun onDestroyView() {
        Timber.i("onDestroyView()")
        super.onDestroyView()
        _binding = null
    }


}
