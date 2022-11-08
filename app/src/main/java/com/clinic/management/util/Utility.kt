package com.clinic.management.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.clinic.management.R

object Utility {
    fun alertBox(context: Context, title: String, message: String, Listener: alertClickListener) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(context.getString(R.string.yes)) { dialogInterface, which ->
            Listener.clickListener()
            dialogInterface.cancel()
        }
        builder.setNegativeButton(context.getString(R.string.no)) { dialogInterface, which ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    interface alertClickListener {
        fun clickListener()
    }
}