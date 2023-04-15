package com.example.photoweathertask.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<item> : RecyclerView.Adapter<BaseViewHolder<item>>() {
    protected var data: MutableList<item> = arrayListOf()

    fun insertAll(insertedItemList: MutableList<item>) {
        data = insertedItemList
        notifyDataSetChanged()
    }

    fun addAll(insertedItemList: MutableList<item>) {
        val dataSize = data.size
        data.addAll(insertedItemList)
        notifyItemRangeInserted(dataSize, insertedItemList.count())
    }

    fun add(insertedItemList: item) {
        val dataSize = data.size
        data.add(insertedItemList)
        notifyItemRangeInserted(dataSize, 1)
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }
}