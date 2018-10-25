package br.brilhante.gustavo.feednews.extensions

import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup


fun ViewGroup.inflate(@LayoutRes res: Int, attachToRoot: Boolean = false) =
    LayoutInflater.from(this.context).inflate(res, this, attachToRoot)