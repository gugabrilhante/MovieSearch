package br.brilhante.gustavo.moviesearch.extensions

import android.app.Activity
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair

fun Activity.makeSceneTransitionAnimation(views: List<View>): ActivityOptionsCompat {
    val pairs = views.map { Pair.create(it, it.transitionName) }.toTypedArray()
    return ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
}
