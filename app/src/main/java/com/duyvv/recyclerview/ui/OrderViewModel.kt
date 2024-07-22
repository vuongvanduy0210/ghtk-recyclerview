package com.duyvv.recyclerview.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyvv.recyclerview.domain.Customer
import com.duyvv.recyclerview.domain.Order
import com.duyvv.recyclerview.ui.adapter.OrderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class OrderViewModel : ViewModel() {

    private val _orders = MutableStateFlow(mutableListOf<Order>())
    val orders = _orders.asStateFlow()

    fun loadMoreOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("OrderViewModel", "CurrentThread: ${Thread.currentThread().name}")
            delay(2000)
            val list = mutableListOf<Order>()
            list.addAll(_orders.value)
            list.addAll(generateOrders())
            _orders.value = list
        }
    }

    fun resetOrders() {
        _orders.value = mutableListOf()
    }

    private fun generateOrders(): List<Order> {
        val orders = mutableListOf<Order>()
        val productNames = listOf(
            "Áo thun", "Quần jeans", "Giày thể thao", "Túi xách", "Điện thoại",
            "Laptop", "Tai nghe", "Đồng hồ", "Sách", "Bút bi"
        )

        val addresses = listOf(
            "123 Đường Lê Lợi, Quận 1, TP. Hồ Chí Minh",
            "456 Đường Trần Hưng Đạo, Quận 5, TP. Hồ Chí Minh",
            "789 Đường Nguyễn Trãi, Quận 3, TP. Hồ Chí Minh",
            "101 Đường Phạm Văn Đồng, Quận Gò Vấp, TP. Hồ Chí Minh",
            "202 Đường Lê Duẩn, Quận Đống Đa, Hà Nội",
            "303 Đường Hai Bà Trưng, Quận Hoàn Kiếm, Hà Nội",
            "404 Đường Lê Văn Lương, Quận Thanh Xuân, Hà Nội",
            "505 Đường Nguyễn Văn Cừ, Quận Long Biên, Hà Nội",
            "606 Đường Trần Phú, Quận Ngô Quyền, Hải Phòng",
            "707 Đường Lạch Tray, Quận Ngô Quyền, Hải Phòng"
        )

        val customerNames = listOf(
            "Nguyễn Văn A", "Trần Thị B", "Lê Văn C", "Phạm Thị D", "Hoàng Văn E",
            "Vũ Thị F", "Đỗ Văn G", "Phan Thị H", "Ngô Văn I", "Đặng Thị K"
        )

        val statuses = listOf("Đã giao", "Đang chờ xử lý", "Đẩy sang GHTK không thành công")

        for (i in 1..20) {
            val type = if (Random.nextInt() % 2 == 0) OrderType.COLLAPSE else OrderType.EXPAND
            val codMoney = (i * 1000).toFloat()
            val productName = productNames[i % productNames.size]
            val address = addresses[i % addresses.size]
            val customerName = customerNames[i % customerNames.size]
            val customer = Customer(
                name = customerName,
                phoneNumber = "09876543${i.toString().padStart(2, '0')}"
            )
            val status = statuses[i % statuses.size]

            val order = Order(
                type = type,
                codMoney = codMoney,
                productName = productName,
                address = address,
                customer = customer,
                status = status
            )

            orders.add(order)
        }

        return orders
    }
}