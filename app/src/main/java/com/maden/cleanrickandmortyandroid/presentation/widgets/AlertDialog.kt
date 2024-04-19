package com.maden.cleanrickandmortyandroid.presentation.widgets

import android.app.AlertDialog
import android.content.Context
import com.maden.cleanrickandmortyandroid.R


fun Context.showAlertDialog(title: String, message: String, positiveButtonAction: () -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(this.getString(R.string.yes)) { dialog, _ ->
        positiveButtonAction.invoke()
        dialog.dismiss()
    }
    builder.setNegativeButton(this.getString(R.string.no)) { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}