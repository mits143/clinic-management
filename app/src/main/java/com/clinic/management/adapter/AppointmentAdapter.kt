package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.R
import com.clinic.management.databinding.ItemActiveBinding
import com.clinic.management.model.appointmments.AppointmentData
import com.clinic.management.util.setDate

class AppointmentAdapter(
    private val dataList: ArrayList<AppointmentData>,
    private var onClick: OnClick,
    private var isActive: Boolean
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemActiveBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.setDate(this.appointmentDate).toString()
                binding.txtSpecialist.text = this.docSpecialization
                binding.txtDate1.text = this.appointmentDate
                Glide.with(itemView.context).asBitmap().load(this.docImage).into(binding.img)
                binding.txtName.text = this.docName
                binding.txtSpecialist1.text = this.docSpecialization
                binding.ratingBar.rating = this.docAvgRating.toFloat()

                if (!isActive) {
                    binding.btnReschedule.visibility = View.GONE
                    binding.btnCancel.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.gradient
                        )
                    )
                }

                binding.btnCancel.setOnClickListener {
                    onClick.itemClick(this)
                }
                binding.btnReschedule.setOnClickListener {
                    onClick.itemClick(this)
                }
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

    fun addData(list: ArrayList<AppointmentData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: AppointmentData)
    }
}