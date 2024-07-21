package com.duyvv.recyclerview.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<B : ViewBinding>(val binding: B) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: Any)
}