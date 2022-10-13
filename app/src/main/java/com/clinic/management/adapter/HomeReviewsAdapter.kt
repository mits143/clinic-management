package com.clinic.management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clinic.management.databinding.ItemRecentReviewsBinding
import com.clinic.management.model.home.ReviewsListing

class HomeReviewsAdapter(
    private val dataList: ArrayList<ReviewsListing>,
    private var onClick: OnClick
) : RecyclerView.Adapter<HomeReviewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecentReviewsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecentReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                Glide.with(itemView.context).asBitmap().load(this.image).into(binding.img)
                binding.btnViews.text = this.rating
                binding.txtName.text = this.name
                binding.txtSpecialist.text = this.description
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

    fun addData(list: ArrayList<ReviewsListing>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClick {
        fun itemClick(data: ReviewsListing)
    }
}