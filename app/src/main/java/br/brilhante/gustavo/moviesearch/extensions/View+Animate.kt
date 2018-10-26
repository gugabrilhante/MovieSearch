package br.brilhante.gustavo.moviesearch.extensions

import android.view.View
import android.view.ViewPropertyAnimator

fun View.animateAlpha(alpha: Float, time: Long = 0): ViewPropertyAnimator {
    return this.animate().alpha(alpha).setDuration(time)
}