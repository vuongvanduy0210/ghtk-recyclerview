package com.duyvv.recyclerview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.duyvv.firstlesson.base.BaseFragment
import com.duyvv.recyclerview.databinding.FragmentOderBinding
import com.duyvv.recyclerview.domain.Oder
import com.duyvv.recyclerview.domain.OderFilter
import com.duyvv.recyclerview.ui.adapter.FilterAdapter
import com.duyvv.recyclerview.ui.adapter.OderAdapter
import com.duyvv.recyclerview.ui.adapter.OderType

class OderFragment : BaseFragment<FragmentOderBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOderBinding.inflate(inflater, container, false)

    override fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {
        setupListFilter()

        setupListOder()
    }

    private fun setupListOder() {
        val oderAdapter = OderAdapter()
        binding.rcvOder.apply {
            adapter = oderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        oderAdapter.addItems(
            listOf(
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.EXPAND),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
                Oder(OderType.COLLAPSE),
            )
        )
    }

    private fun setupListFilter() {
        val filterAdapter = FilterAdapter()
        binding.rcvOderFilter.apply {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        filterAdapter.setItems(
            listOf(
                OderFilter("Thời gian"),
                OderFilter("Đã chốt"),
                OderFilter("Đang chốt"),
                OderFilter("Đã hoàn thành"),
                OderFilter("Chưa hoàn thành"),
            )
        )
    }
}