package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.R
import com.clinic.management.databinding.ItemActiveBinding
import com.clinic.management.model.appointmments.ActiveAppointmentData
import com.clinic.management.util.setDate

class AppointmentAdapter(
    private val dataList: ArrayList<ActiveAppointmentData>,
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
                binding.txtNo.text = this.position
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
                    onClick.itemClick(this, "CANCEL", position)
                }
                binding.btnReschedule.setOnClickListener {
                    onClick.itemClick(this, "RESCHEDULE", position)
                }
                binding.llPosition.setOnClickListener {
                    onClick.itemClick(this, "POSITIONLIST", position)
                }
//                itemView.setOnClickListener {
//                    onClick.itemClick(this)
//                }
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

    fun addData(list: ArrayList<ActiveAppointmentData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<ActiveAppointmentData>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: ActiveAppointmentData, type: String, position: Int)
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}