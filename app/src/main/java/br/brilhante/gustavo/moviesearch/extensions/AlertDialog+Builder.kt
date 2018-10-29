package br.brilhante.gustavo.moviesearch.extensions

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun buildAlertDialog(context: Context, title: String, message: String): AlertDialog.Builder {
    val dialog = AlertDialog.Builder(context)
    dialog.setMessage(message)
    dialog.setTitle(title)

    return dialog
}

