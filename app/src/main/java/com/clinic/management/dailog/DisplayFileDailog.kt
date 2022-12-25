package com.clinic.management.dailog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.clinic.management.databinding.DialogViewFileBinding
import java.io.File

class DisplayFileDailog(file: String) : DialogFragment() {

    lateinit var binding: DialogViewFileBinding
    var file = file
    var downloadFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
        binding = DialogViewFileBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(context, config)*/

        if (file.contains(".pdf")) {
            binding.image.visibility = View.GONE
            binding.pdfView.visibility = View.VISIBLE
            binding.pdfView.settings.javaScriptEnabled = true
            binding.pdfView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$file")
            binding.pdfView.settings.builtInZoomControls = true;
            /*binding.pdfView.visibility = View.VISIBLE
            downloadPdfFromInternet(
                file,
                getRootDirPath(requireContext()),
                "$file.pdf"
            )*/
        } else {
            binding.image.visibility = View.VISIBLE
            binding.pdfView.visibility = View.GONE
            Glide.with(this).load(file).into(binding.image)
        }

        binding.imgClose.setOnClickListener {
            dialog?.cancel()
        }
    }


    /*private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    downloadFile = File(dirPath, fileName)
                    showPdfFromFile(downloadFile!!)
                }

                override fun onError(error: com.downloader.Error?) {
                    Toast.makeText(
                        requireContext(),
                        "Error in downloading file :" + error!!.serverErrorMessage,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    context,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

    private fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }*/

}