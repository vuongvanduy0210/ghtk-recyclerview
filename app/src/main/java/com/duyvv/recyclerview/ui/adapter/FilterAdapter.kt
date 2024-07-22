package com.duyvv.recyclerview.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duyvv.recyclerview.base.BaseViewHolder
import com.duyvv.recyclerview.databinding.ItemFilterBinding
import com.duyvv.recyclerview.domain.OrderFilter

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterVH>() {

    inner class FilterVH(binding: ItemFilterBinding) : BaseViewHolder<ItemFilterBinding>(binding) {
        override fun bind(item: Any) {
            val filter = item as OrderFilter
            binding.tvTitle.text = filter.title
        }
    }

    private val items = mutableListOf<OrderFilter>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<OrderFilter>) {
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
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}