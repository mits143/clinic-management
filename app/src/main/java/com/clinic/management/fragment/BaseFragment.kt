package com.clinic.management.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.clinic.management.util.Callback
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


abstract class BaseFragment<B : ViewBinding> : DialogFragment(), CoroutineScope by CoroutineScope(
    Dispatchers.Main
) {

    private lateinit var callback: Callback
    private lateinit var dexter: DexterBuilder

    protected lateinit var binding: B
        private set

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(msg: Int) {
        Toast.makeText(context, getString(msg), Toast.LENGTH_SHORT).show()
    }

    fun getPermission() {
        dexter = Dexter.withContext(requireContext())
            .withPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    report.let {
                        if (report.areAllPermissionsGranted()) {
                            callback.permissionGranted()
                        } else {
                            AlertDialog.Builder(requireContext())
                                .apply {
                                    setMessage("please allow the required permissions")
                                        .setCancelable(false)
                                        .setPositiveButton("Settings") { _, _ ->
                                            val reqIntent =
                                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                                    .apply {
                                                        val uri = Uri.fromParts(
                                                            "package",
                                                            requireContext().packageName,
                                                            null
                                                        )
                                                        data = uri
                                                    }
                                            resultLauncher.launch(reqIntent)
                                        }
//                                     setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
                                    val alert = this.create()
                                    alert.show()
                                }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).withErrorListener {
                showToast(it.name)
            }
        dexter.check()
    }

    fun setUpListener(callback: Callback) {
        this.callback = callback
    }

    fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        getCaptureResult.launch(intent)
    }

    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        getBrowseResult.launch(intent)
    }

    private val getCaptureResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                callback.captureImageData(intent)
            }
        }

    private val getBrowseResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                callback.browseImageData(intent)
            }
        }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            dexter.check()
        }

    override fun onDestroyView() {
        coroutineContext[Job]?.cancel()
        super.onDestroyView()
    }
}