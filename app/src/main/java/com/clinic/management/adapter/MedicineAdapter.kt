package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.databinding.ItemMedicineBinding
import com.clinic.management.model.medicine.MedicineData

class MedicineAdapter(
    private val dataList: ArrayList<MedicineData>,
    private var onClick: OnClick
) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtName.text = this.name
                binding.txtSolution.text = this.shortDescription
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

    fun addData(list: ArrayList<MedicineData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<MedicineData>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: MedicineData)
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }
}