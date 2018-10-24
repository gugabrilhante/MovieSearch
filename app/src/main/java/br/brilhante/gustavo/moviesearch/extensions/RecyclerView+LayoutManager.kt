package br.brilhante.gustavo.feednews.extensions

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

fun RecyclerView.verticalLinearLayout(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = RecyclerView.VERTICAL
    this.layoutManager = linearLayoutManager
}

fun RecyclerView.horizontalLinearLayout(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = RecyclerView.HORIZONTAL
    this.layoutManager = linearLayoutManager
}