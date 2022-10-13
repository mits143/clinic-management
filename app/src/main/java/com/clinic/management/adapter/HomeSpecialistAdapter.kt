package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.databinding.ItemSpecialistBinding
import com.clinic.management.model.home.SpecialCategory

class HomeSpecialistAdapter(
    private val dataList: ArrayList<SpecialCategory>,
    private var onClick: OnClick
) : RecyclerView.Adapter<HomeSpecialistAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSpecialistBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSpecialistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                Glide.with(itemView.context).asBitmap().load(this.image).into(binding.img)
                binding.txtName.text = this.displayName
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

    fun addData(list: ArrayList<SpecialCategory>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: SpecialCategory)
    }
}