package com.duyvv.recyclerview.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding
import com.duyvv.recyclerview.base.BaseViewHolder
import com.duyvv.recyclerview.databinding.ItemCollapseOrderBinding
import com.duyvv.recyclerview.databinding.ItemExpandOrderBinding
import com.duyvv.recyclerview.domain.Order
import com.duyvv.recyclerview.utils.toMoneyType

@SuppressLint("NotifyDataSetChanged")
class OrderAdapter : Adapter<BaseViewHolder<out ViewBinding>>() {

    inner class CollapseOrderVH(binding: ItemCollapseOrderBinding) :
        BaseViewHolder<ItemCollapseOrderBinding>(binding) {

        override fun bind(item: Any) {
            val order = item as Order
            binding.apply {
                tvName.text = order.customer.name
                tvPhoneNumber.text = order.customer.phoneNumber
            }
        }
    }

    inner class ExpandOrderVH(binding: ItemExpandOrderBinding) :
        BaseViewHolder<ItemExpandOrderBinding>(binding) {

        override fun bind(item: Any) {
            val order = item as Order
            binding.apply {
                tvMoney.text =
                    "Tiền CoD: ${order.codMoney.toMoneyType()} đ / SP: ${order.productName}"
                tvStatus.text = "Trạng thái: ${order.status}"
                tvAddress.text = "Địa chỉ: ${order.address}"
            }
        }
    }

    private val items = mutableListOf<Order>()

    fun setItems(items: List<Order>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Order>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewBinding> {
        return when (viewType) {
            TYPE_COLLAPSE -> CollapseOrderVH(
                ItemCollapseOrderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_EXPAND -> ExpandOrderVH(
                ItemExpandOrderBinding.inflate(
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
            OrderType.COLLAPSE -> TYPE_COLLAPSE
            OrderType.EXPAND -> TYPE_EXPAND
        }
    }

    companion object {
        private const val TYPE_COLLAPSE = 0
        private const val TYPE_EXPAND = 1
    }
}


enum class OrderType {
    COLLAPSE, EXPAND
}