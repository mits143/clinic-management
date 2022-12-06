package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.databinding.ItemMedicinePrescriptionBinding
import com.clinic.management.model.doctorResult.PrescriptionMedicine

class MedicinePrescriptionAdapter(
    private val dataList: ArrayList<PrescriptionMedicine>,
) : RecyclerView.Adapter<MedicinePrescriptionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMedicinePrescriptionBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMedicinePrescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtName.text = this.medicine_name
                binding.txtMedicineTime.text = this.take_dosage
                binding.txtFrequency.text = this.frequency
                binding.txtNotes.text = this.medicine_notes
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

    fun addData(list: ArrayList<PrescriptionMedicine>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<PrescriptionMedicine>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: PrescriptionMedicine, type: String, position: Int)
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}