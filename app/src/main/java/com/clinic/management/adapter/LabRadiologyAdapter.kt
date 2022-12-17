package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.R
import com.clinic.management.databinding.ItemImagesBinding
import com.clinic.management.model.lab.PathologyLabResult

class LabRadiologyAdapter(
    private val dataList: ArrayList<PathologyLabResult>,
    private var onClick: OnClick
) : RecyclerView.Adapter<LabRadiologyAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImagesBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                if (this.pathology_lab_result!!.contains(".pdf"))
                    Glide.with(itemView.context).asBitmap().load(R.drawable.pdf)
                        .into(binding.img)
                else
                    Glide.with(itemView.context).asBitmap().load(this.pathology_lab_result)
                        .into(binding.img)

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

    fun addData(list: ArrayList<PathologyLabResult>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: PathologyLabResult)
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}