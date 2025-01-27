package com.yusufcanmercan.weight_track_app.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun View.showWithAnim(duration: Long = 150) {
    this.scaleX = 0f
    this.scaleY = 0f
    this.alpha = 0f
    this.visibility = View.VISIBLE

    this.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator()).setListener(null)
}

fun View.hideWithAnim(duration: Long = 150) {
    this.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@hideWithAnim.visibility = View.GONE
            }
        })
}
