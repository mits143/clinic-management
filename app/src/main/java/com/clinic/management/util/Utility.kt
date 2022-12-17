package com.clinic.management.util

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.alhanpos.store.util.FileUtils
import com.clinic.management.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

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

    fun prepareFilePart(
        context: Activity,
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part? {
        val file = FileUtils.getFile(context, fileUri)
        if (file != null) {
            val requestFile: RequestBody =
                file.asRequestBody(FileUtils.MIME_TYPE_IMAGE.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(partName, file.name, requestFile)
        }
        return null
    }
}