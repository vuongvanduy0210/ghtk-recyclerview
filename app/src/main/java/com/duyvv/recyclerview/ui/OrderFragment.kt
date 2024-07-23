package com.duyvv.recyclerview.ui

import android.os.Bundle
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
import kotlin.random.Random

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderBinding.inflate(inflater, container, false)

    private val orderViewModel: OrderViewModel by viewModels()

    private var isLoadMore = false
    private val pageSize = 20
    private var pageNum = 1
    private val totalItem = Random.nextInt(100, 151)
    private val totalPage = countTotalPage()

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
            orderViewModel.loadMoreOrders(pageSize)
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
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
                if (lastVisibleItemPosition == (pageNum - 1) * pageSize + 15 &&
                    pageNum < totalPage &&
                    !isLoadMore
                ) {
                    isLoadMore = true
                    orderViewModel.loadMoreOrders(
                        // last page is contain less than pageSize
                        if (pageNum == totalPage - 1 && totalItem % pageSize > 0) {
                            totalItem % pageSize
                        } else {
                            pageSize
                        }
                    )
                    isLoadMore = false
                    pageNum++
                }
            }
        }
        binding.rcvOrder.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext())
            orderViewModel.loadMoreOrders(pageSize)
            addOnScrollListener(onScrollListener)
        }
    }

    private fun countTotalPage(): Int {
        return if (totalItem % pageSize > 0) {
            totalItem / pageSize + 1
        } else {
            totalItem / pageSize
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