package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.databinding.ItemSubActiveBinding
import com.clinic.management.model.radiology.BookingInformation
import com.clinic.management.util.setDate

class SubRadiologyResultAdapter(
    private val dataList: ArrayList<BookingInformation>,
    private var radioName: String,
    private var radioLogo: String,
    private var radioRating: String,
    private var onclick: OnClick
) : RecyclerView.Adapter<SubRadiologyResultAdapter.ViewHolder>() {

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
                binding.txtDate.setDate(this.appointment_date!!).toString()
                binding.txtSpecialist.text = doc_specialization
                binding.txtDate1.text = this.appointment_date
                this.radiology_name = radioName
                this.radiology_logo = radioLogo
                this.radiology_rating = radioRating
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