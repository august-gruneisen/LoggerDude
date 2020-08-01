package com.augustg.rluda

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private var startButtonFadeInDurationMillis: Long = 50
    private var startButtonFadeOutDurationMillis: Long = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
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