package com.clinic.management.util

import android.content.Intent

interface Callback {
    fun captureImageData(uri: Intent?)
    fun browseImageData(uri: Intent?)
    fun pdfData(uri: Intent?)
    fun permissionGranted()
    fun permissionNotGranted()
}