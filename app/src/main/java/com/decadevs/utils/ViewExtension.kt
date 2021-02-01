package com.decadevs.utils

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.decadevs.healthrecords.R

fun Activity.showAlertDialog(
    message: String,
    title: String,
    dialogInterface: DialogInterface.OnClickListener
) {
    val dialogBuilder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
    dialogBuilder.setMessage(message)
        .setPositiveButton("Yes", dialogInterface)
        .setNegativeButton("Cancel", null)


    val alert = dialogBuilder.create()
    alert.setTitle(title)
    alert.show()
}
