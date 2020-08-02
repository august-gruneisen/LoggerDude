package com.augustg.rluda.util

/**
 * @author  August Gruneisen
 * @since   2020-07-31
 */
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

/**
 * Fades in a View
 * View should already have alpha=0
 *
 * @param duration (ms)
 * @param delayBefore (ms)
 * @param onAnimationEnd (optional)
 */
fun View.fadeIn(
    duration: Long,
    delayBefore: Long,
    onAnimationEnd: (() -> Unit) = {/* no-op */ }
) {
    this.apply {
        animate()
            .alpha(1f)
            .setStartDelay(delayBefore)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onAnimationEnd.invoke()
                }
            })
    }
}

/**
 * Fades out a View
 * View should already have alpha=1
 *
 * @param duration (ms)
 * @param delayBefore (ms)
 * @param onAnimationEnd (optional)
 */
fun View.fadeOut(
    duration: Long,
    delayBefore: Long,
    onAnimationEnd: (() -> Unit) = {/* no-op */ }
) {
    this.apply {
        animate()
            .alpha(0f)
            .setStartDelay(delayBefore)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onAnimationEnd.invoke()
                }
            })
    }
}

/**
 * Pulses a View after some optional delay
 * View should already have alpha=0
 *
 * @param fadeInDuration (ms)
 * @param fadeOutDuration (ms)
 * @param delayBetween (ms)
 * @param initialDelay (optional)
 */
fun View.pulse(
    fadeInDuration: Long,
    fadeOutDuration: Long,
    delayBetween: Long,
    initialDelay: Long = 0
) {
    this.apply {
        fadeIn(fadeInDuration, initialDelay) {
            pulse(fadeInDuration, fadeOutDuration, delayBetween)
            fadeOut(fadeOutDuration, delayBetween) {
                this.pulse(fadeInDuration, fadeOutDuration, delayBetween)
            }
        }
    }
}

/**
 * Toggles the visibility of a View
 */
fun View.toggleVisibility() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}
