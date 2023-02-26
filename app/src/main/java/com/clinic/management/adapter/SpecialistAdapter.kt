package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.databinding.ItemFilterBinding
import com.clinic.management.model.home.SpecialCategory

class SpecialistAdapter(
    private val dataList: ArrayList<SpecialCategory>, private var onClick: OnClick
) : RecyclerView.Adapter<SpecialistAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.checkbox.visibility = View.VISIBLE
                binding.txtFilter.text = this.displayName
                binding.checkbox.isChecked = isChecked
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

    fun getSelectedItem(): ArrayList<SpecialCategory> {
        var list: ArrayList<SpecialCategory> = arrayListOf()
        dataList.forEach {
            if (it.isChecked) {
                list.add(it)
            }
        }
        return list
    }

    interface OnClick {
        fun itemClick(data: SpecialCategory)
    }
}