package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.R
import com.clinic.management.databinding.ItemFilterBinding

class FilterAdapter(
    private val dataList: ArrayList<String>, private var onClick: OnClick
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    var index = 0

    inner class ViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtFilter.text = this
                if (index == position) {
                    holder.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.white
                        )
                    );
                } else {
                    holder.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.grey
                        )
                    );
                }
                itemView.setOnClickListener {
                    onClick.itemClick(this)
                    index = position;
                    notifyDataSetChanged();
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

    fun addData(list: ArrayList<String>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: String)
    }
}