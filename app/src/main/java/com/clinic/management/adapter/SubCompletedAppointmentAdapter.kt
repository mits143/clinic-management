package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.databinding.ItemSubActiveBinding
import com.clinic.management.model.appointmments.BookingInformation
import com.clinic.management.util.setDate

class SubCompletedAppointmentAdapter(
    private val dataList: ArrayList<BookingInformation>,
    private var doc_specialization: String,
    private var onclick: OnClick
) : RecyclerView.Adapter<SubCompletedAppointmentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSubActiveBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSubActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.setDate(this.appointmentDate).toString()
                binding.txtSpecialist.text = doc_specialization
                binding.txtDate1.text = this.appointmentDate

                itemView.setOnClickListener {
                    onclick.itemClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<BookingInformation>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: BookingInformation)
    }
}