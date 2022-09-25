package com.clinic.management.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clinic.management.R


class MainAdapter(
    private val users: ArrayList<String>,
    private var type: String,
    private var onClick: OnClick
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: String, type: String, onClick: OnClick) {
            if (TextUtils.equals(type, "Doctor")) {
                itemView.setOnClickListener {
                    onClick.itemClick()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            if (TextUtils.equals(type, "Doctor") || TextUtils.equals(type, "Top Doctor")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_specialist_dr, parent, false)
            } else if (TextUtils.equals(type, "Specialist")) {
                LayoutInflater.from(parent.context).inflate(R.layout.item_specialist, parent, false)
            } else if (TextUtils.equals(type, "Review")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recent_reviews, parent, false)
            } else if (TextUtils.equals(type, "Appointment")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_active, parent, false)
            } else {
                throw IllegalArgumentException("Invalid type")
            }
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position], type, onClick)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<String>) {
        users.clear()
        users.add(0, "Add")
        users.addAll(list)
//        notifyItemInserted(list.size - itemCount - 1);
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick();
    }
}