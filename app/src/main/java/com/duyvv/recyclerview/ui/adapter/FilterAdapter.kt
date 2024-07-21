package com.duyvv.recyclerview.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.duyvv.recyclerview.databinding.ItemFilterBinding
import com.duyvv.recyclerview.domain.OderFilter

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterVH>() {

    inner class FilterVH(val binding: ItemFilterBinding) : ViewHolder(binding.root)

    private val items = mutableListOf<OderFilter>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<OderFilter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterVH {
        return FilterVH(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterVH, position: Int) {
        val item = items[position]
        holder.binding.tvTitle.text = item.title
    }

    override fun getItemCount() = items.size
}