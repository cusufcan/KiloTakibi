package com.yusufcanmercan.weight_track_app.ui.view.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.yusufcanmercan.weight_track_app.databinding.FragmentGraphBinding
import com.yusufcanmercan.weight_track_app.ui.viewmodel.GraphViewModel
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import com.yusufcanmercan.weight_track_app.util.graph.CustomLineChart


class GraphFragment : Fragment() {
    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private lateinit var lineChart: CustomLineChart

    private val weightViewModel: WeightViewModel by activityViewModels()
    private val graphViewModel: GraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        fetchData()
        setupChart()
    }

    private fun bindViews() {
        lineChart = binding.lineChart
    }

    private fun fetchData() {
        graphViewModel.fetchData(weightViewModel)
        graphViewModel.setupLineData(requireActivity())
    }

    private fun setupChart() {
        val data = graphViewModel.chartData
        val values = graphViewModel.weights.map { it.date }.toTypedArray()

        lineChart.setupChartData(data)
        lineChart.setupChartAxises(values)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}