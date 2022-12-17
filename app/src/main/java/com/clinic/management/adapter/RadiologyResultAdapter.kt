package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.databinding.ItemCompletedBinding
import com.clinic.management.model.radiology.BookingInformation
import com.clinic.management.model.radiology.RadiologyData

class RadiologyResultAdapter(
    private val dataList: ArrayList<RadiologyData>,
    private var onClick: OnClick
) : RecyclerView.Adapter<RadiologyResultAdapter.ViewHolder>() {

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
                    SubRadiologyResultAdapter(
                        this.booking_information,
                        this.radiology_name!!,
                        radiology_logo!!,
                        radiology_avg_rating.toString(),
                        object : SubRadiologyResultAdapter.OnClick {
                            override fun itemClick(data: BookingInformation) {
                                onClick.itemChildClick(data)
                            }

                        })
                binding.rvInfo.adapter = adapter
                Glide.with(itemView.context).asBitmap().load(this.radiology_logo).into(binding.img)
                binding.txtName.text = this.radiology_name
                binding.txtSpecialist1.text = this.radiology_address
                binding.ratingBar.rating = this.radiology_avg_rating!!.toFloat()
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

    fun addData(list: ArrayList<RadiologyData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<RadiologyData>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: RadiologyData)
        fun itemChildClick(data: BookingInformation)
    }
}