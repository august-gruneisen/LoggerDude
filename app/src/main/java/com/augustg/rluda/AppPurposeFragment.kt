package com.augustg.rluda

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.augustg.rluda.databinding.FragmentAppPurposeBinding

class AppPurposeFragment: Fragment() {

    private lateinit var binding: FragmentAppPurposeBinding

    private lateinit var startButton: Button
    private var startButtonFadeInDurationMillis: Long = 50
    private var startButtonFadeOutDurationMillis: Long = 400

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppPurposeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startButton = binding.startButton

        startButton.setOnClickListener {
            Toast.makeText(requireContext(), "Toast", Toast.LENGTH_SHORT).show()
        }

        fadeIn(startButton)
    }

    private fun fadeIn(view: View) {
        view.apply {
            alpha = 0f
            animate()
                .alpha(1f)
                .setStartDelay(0)
                .setDuration(startButtonFadeInDurationMillis)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        fadeOut(view)
                    }
                })
        }
    }

    private fun fadeOut(view: View) {
        view.apply {
            animate()
                .alpha(0f)
                .setStartDelay(3000)
                .setDuration(startButtonFadeOutDurationMillis)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        fadeIn(view)
                    }
                })
        }
    }
}