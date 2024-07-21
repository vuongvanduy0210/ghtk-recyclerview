package com.duyvv.recyclerview.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding
import com.duyvv.recyclerview.base.BaseViewHolder
import com.duyvv.recyclerview.databinding.ItemCollapseOderBinding
import com.duyvv.recyclerview.databinding.ItemExpandOderBinding
import com.duyvv.recyclerview.domain.Oder

@SuppressLint("NotifyDataSetChanged")
class OderAdapter : Adapter<BaseViewHolder<out ViewBinding>>() {

    inner class CollapseOderVH(binding: ItemCollapseOderBinding) :
        BaseViewHolder<ItemCollapseOderBinding>(binding) {
        override fun bind(item: Any) {

        }
    }

    inner class ExpandOderVH(binding: ItemExpandOderBinding) :
        BaseViewHolder<ItemExpandOderBinding>(binding) {
        override fun bind(item: Any) {
        }
    }

    private val items = mutableListOf<Oder>()

    fun setItems(items: List<Oder>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Oder>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewBinding> {
        return when (viewType) {
            TYPE_COLLAPSE -> CollapseOderVH(
                ItemCollapseOderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_EXPAND -> ExpandOderVH(
                ItemExpandOderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out ViewBinding>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            OderType.COLLAPSE -> TYPE_COLLAPSE
            OderType.EXPAND -> TYPE_EXPAND
        }
    }

    companion object {
        private const val TYPE_COLLAPSE = 0
        private const val TYPE_EXPAND = 1
    }
}


enum class OderType {
    COLLAPSE, EXPAND
}