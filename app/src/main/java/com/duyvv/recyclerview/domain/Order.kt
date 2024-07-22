package com.duyvv.recyclerview.domain

import com.duyvv.recyclerview.ui.adapter.OrderType

data class Order(
    val type: OrderType,
    val codMoney: Float,
    val productName: String,
    val address: String,
    val customer: Customer,
    val status: String,
)
