package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.databinding.ItemSpecialistDrBinding
import com.clinic.management.model.home.SpecialistDoctor

class HomeSpecialistDoctorAdapter(
    private val dataList: ArrayList<SpecialistDoctor>, private var onClick: OnClick
) : RecyclerView.Adapter<HomeSpecialistDoctorAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSpecialistDrBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSpecialistDrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                Glide.with(itemView.context).asBitmap().load(this.profileImage).into(binding.img)
                binding.txtName.text = this.docName
                binding.txtSpecialist.text = this.degree
                binding.txtSpecialist1.text = this.specialization
                binding.ratingBar.rating = this.avgRating.toFloat()
                itemView.setOnClickListener {
                    onClick.itemClick(this, "DOCTOR_DETAIL")
                }
                binding.btnViewAllSpecialist.setOnClickListener {
                    onClick.itemClick(this, "BOOK_APPOINTMENT")
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

    fun addData(list: ArrayList<SpecialistDoctor>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<SpecialistDoctor>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: SpecialistDoctor, string: String)
    }
}