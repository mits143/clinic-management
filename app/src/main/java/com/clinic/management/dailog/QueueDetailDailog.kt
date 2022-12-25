package com.clinic.management.dailog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.clinic.management.adapter.QueueAdapter
import com.clinic.management.databinding.DialogQueueDetailsBinding
import com.clinic.management.model.appointmments.ActivePositionListItem

class QueueDetailDailog(dataList: ArrayList<ActivePositionListItem>) : DialogFragment() {

    lateinit var binding: DialogQueueDetailsBinding
    var dataList = dataList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
        binding = DialogQueueDetailsBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(
            width, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgClose.setOnClickListener {
            dialog?.cancel()
        }
        setQueueData(dataList)
    }

    private fun setQueueData(dataList: ArrayList<ActivePositionListItem>) {
        val adapter = QueueAdapter(arrayListOf())
        binding.rvQueue.adapter = adapter
        adapter.addData(dataList)
    }
}