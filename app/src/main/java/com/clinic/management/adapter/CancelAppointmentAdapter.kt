package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.databinding.ItemCompletedBinding
import com.clinic.management.model.appointmments.BookingInformation
import com.clinic.management.model.appointmments.CancelAppointmentData

class CancelAppointmentAdapter(
    private val dataList: ArrayList<CancelAppointmentData>,
    private var onClick: OnClick
) : RecyclerView.Adapter<CancelAppointmentAdapter.ViewHolder>() {

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
                    SubCompletedAppointmentAdapter(this.bookingInformation, this.docSpecialization,
                        object : SubCompletedAppointmentAdapter.OnClick {
                            override fun itemClick(data: BookingInformation) {
                            }

                        })
                binding.rvInfo.adapter = adapter
                Glide.with(itemView.context).asBitmap().load(this.docImage).into(binding.img)
                binding.txtName.text = this.docName
                binding.txtSpecialist1.text = this.docSpecialization
                binding.ratingBar.rating = this.docAvgRating.toFloat()
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

    fun addData(list: ArrayList<CancelAppointmentData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<CancelAppointmentData>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: CancelAppointmentData)
    }
}