package com.ballchalu.ui.razorpay

import android.content.Context
import android.content.DialogInterface
import com.ballchalu.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showMessageDialog(
    listener: DialogInterface.OnClickListener
) {
    val builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
    builder.setMessage("Do you want to cancel the payment?")
        .setTitle("Cancel Payment")
        .setCancelable(false)
        .setNegativeButton("No") { dialog, _: Int -> dialog.dismiss() }
        .setPositiveButton("Yes", listener)
    val dialog = builder.create()
    dialog.show()
}
