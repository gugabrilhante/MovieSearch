package br.brilhante.gustavo.moviesearch.extensions

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.content.ContextCompat


fun View.animateAlpha(alpha: Float, time: Long = 0): ViewPropertyAnimator {
    return this.animate().alpha(alpha).setDuration(time)
}

fun View.setBackgroundAnimated(duration: Long = 100L, fromColorId: Int, toColorId: Int) {
    ObjectAnimator.ofObject(
        this,
        "backgroundColor",
        ArgbEvaluator(),
        ContextCompat.getColor(this.context, fromColorId),
        ContextCompat.getColor(this.context, toColorId)
    )
        .setDuration(duration)
        .start()
}