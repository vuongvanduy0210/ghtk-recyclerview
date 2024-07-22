package com.duyvv.recyclerview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.duyvv.recyclerview.base.BaseFragment
import com.duyvv.recyclerview.databinding.FragmentOrderBinding
import com.duyvv.recyclerview.domain.OrderFilter
import com.duyvv.recyclerview.ui.adapter.FilterAdapter
import com.duyvv.recyclerview.ui.adapter.OrderAdapter
import kotlinx.coroutines.launch

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderBinding.inflate(inflater, container, false)

    private val orderViewModel: OrderViewModel by viewModels()

    private var isLoadMore = false
    private val pageSize = 20
    private var pageNum = 1
    private val totalPage = 5

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }

    override fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {
        setupListFilter()

        setupListener()

        setupListOrder()

        setupObserver()
    }

    private fun setupListener() {
        binding.listOderSwipeLayout.setOnRefreshListener {
            pageNum = 1
            isLoadMore = false
            orderViewModel.resetOrders()
            orderViewModel.loadMoreOrders()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            Log.d("OrderFragment", "CurrentThread: ${Thread.currentThread().name}")
            orderViewModel.orders.collect {
                orderAdapter.setItems(it)
                if (it.isNotEmpty())
                    binding.listOderSwipeLayout.isRefreshing = false
            }
        }
    }

    private fun setupListOrder() {
        val onScrollListener = object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
                Log.d("TAG", "onScrolled: $lastVisibleItemPosition")
                if (lastVisibleItemPosition == (pageNum - 1) * pageSize + 15 &&
                    pageNum < totalPage &&
                    !isLoadMore
                ) {
                    isLoadMore = true
                    orderViewModel.loadMoreOrders()
                    isLoadMore = false
                    pageNum++
                    Log.d("TAG", "PageNum: $pageNum")
                    Log.d("TAG", "List order size: ${orderViewModel.orders.value.size}")
                }
            }
        }
        binding.rcvOrder.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext())
            orderViewModel.loadMoreOrders()
            addOnScrollListener(onScrollListener)
        }
    }

    private fun setupListFilter() {
        val filterAdapter = FilterAdapter()
        binding.rcvOrderFilter.apply {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        filterAdapter.setItems(
            listOf(
                OrderFilter("Thời gian"),
                OrderFilter("Đã chốt"),
                OrderFilter("Đang chốt"),
                OrderFilter("Đã hoàn thành"),
                OrderFilter("Chưa hoàn thành"),
            )
        )
    }
}