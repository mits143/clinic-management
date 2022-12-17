package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.databinding.ItemCompletedBinding
import com.clinic.management.model.lab.BookingInformation
import com.clinic.management.model.lab.LabData

class LabResultAdapter(
    private val dataList: ArrayList<LabData>,
    private var onClick: OnClick
) : RecyclerView.Adapter<LabResultAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCompletedBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCompletedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                val adapter =
                    SubLabResultAdapter(
                        this.booking_information,
                        this.pathology_name!!,
                        this.pathology_logo!!,
                        this.pathology_avg_rating.toString()!!,
                        object : SubLabResultAdapter.OnClick {
                            override fun itemClick(data: BookingInformation) {
                                onClick.itemChildClick(data)
                            }

                        })
                binding.rvInfo.adapter = adapter
                Glide.with(itemView.context).asBitmap().load(this.pathology_logo).into(binding.img)
                binding.txtName.text = this.pathology_name
                binding.txtSpecialist1.text = this.pathology_address
                binding.ratingBar.rating = this.pathology_avg_rating!!.toFloat()
                binding.divider.visibility = View.GONE
                binding.llButton.visibility = View.GONE
                itemView.setOnClickListener {
                    onClick.itemClick(this)
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

    fun addData(list: ArrayList<LabData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<LabData>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: LabData)
        fun itemChildClick(data: BookingInformation)
    }

}