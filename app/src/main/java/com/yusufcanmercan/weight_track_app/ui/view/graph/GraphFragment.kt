package com.yusufcanmercan.weight_track_app.ui.view.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.yusufcanmercan.weight_track_app.databinding.FragmentGraphBinding

class GraphFragment : Fragment() {
    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private lateinit var btn1W: MaterialButton
    private lateinit var btn1M: MaterialButton
    private lateinit var btn3M: MaterialButton
    private lateinit var btn1Y: MaterialButton
    private lateinit var btn3Y: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindEvents()
        setStartDefaults()
    }

    private fun bindViews() {
        btn1W = binding.btn1W
        btn1M = binding.btn1M
        btn3M = binding.btn3M
        btn1Y = binding.btn1Y
        btn3Y = binding.btn3Y
    }

    private fun bindEvents() {
        btn1W.setOnClickListener {}
        btn1M.setOnClickListener {}
        btn3M.setOnClickListener {}
        btn1Y.setOnClickListener {}
        btn3Y.setOnClickListener {}
    }

    private fun setStartDefaults() {
        btn1W.isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}