package com.yusufcanmercan.weight_track_app.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.yusufcanmercan.weight_track_app.R

fun showAlertDialog(
    context: Context,
    title: String,
    message: String,
    positiveButtonClickListener: () -> Unit,
) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(context.getString(R.string.yes)) { dialog, _ ->
        positiveButtonClickListener()
        dialog.dismiss()
    }
    builder.setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}

fun showSnackbar(v: View, message: String) {
    Snackbar.make(
        v, message, Snackbar.LENGTH_SHORT
    ).show()
}