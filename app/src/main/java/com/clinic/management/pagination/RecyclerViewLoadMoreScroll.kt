package com.clinic.management.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreScroll(private val mLayoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {
    private val visibleThreshold = 1
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    var loaded = false
        private set
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    fun setLoaded() {
        loaded = false
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return
        totalItemCount = mLayoutManager.itemCount
        if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
        }
        if (!loaded && totalItemCount == lastVisibleItem + visibleThreshold) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener!!.onLoadMore()
            }
            loaded = true
        }
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}