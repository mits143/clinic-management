package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.databinding.RowAppointmentTimeBinding
import com.clinic.management.model.appointmentslots.Afternoon
import com.clinic.management.util.setDate

class AfternoonAdapter(
    private val dataList: ArrayList<Afternoon>,
    private var onClick: OnClick,
) : RecyclerView.Adapter<AfternoonAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowAppointmentTimeBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowAppointmentTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtTime.text = this.time
                binding.txtTime.isChecked = this.isChecked
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

    fun addData(list: ArrayList<Afternoon>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<Afternoon>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: Afternoon)
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun clearSelectedItems() {
        dataList.forEach {
            it.isChecked = false
        }
        notifyDataSetChanged()
    }
}