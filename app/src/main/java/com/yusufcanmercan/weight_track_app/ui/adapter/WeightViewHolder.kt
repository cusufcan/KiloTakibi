package com.yusufcanmercan.weight_track_app.ui.adapter

import android.graphics.Color
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.WeightListItemBinding
import com.yusufcanmercan.weight_track_app.util.helper.formatWeight
import com.yusufcanmercan.weight_track_app.util.helper.hideWithAnim
import com.yusufcanmercan.weight_track_app.util.helper.showWithAnim

class WeightViewHolder(
    private val binding: WeightListItemBinding,
    private val onDeleteClick: (Weight) -> Unit,
    private val onLongClick: (Weight) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var ivIcon: ImageView
    private lateinit var ivDelete: ImageView
    private lateinit var tvWeight: MaterialTextView
    private lateinit var tvDate: MaterialTextView

    fun bind(weight: Weight, isSelected: Boolean, onWeightClick: () -> Unit) {
        bindViews(weight, isSelected)
        bindEvents(weight, onWeightClick)
    }

    private fun bindViews(weight: Weight, isSelected: Boolean) {
        ivIcon = binding.ivIcon
        ivDelete = binding.ivDelete
        tvWeight = binding.tvWeight
        tvDate = binding.tvDate

        setupViews(weight, isSelected)
    }

    private fun bindEvents(weight: Weight, onWeightClick: () -> Unit) {
        handleWeightClick(onWeightClick)
        handleDeleteClick(weight)
        handleLongClick(weight)
    }

    private fun setupViews(weight: Weight, isSelected: Boolean) {
        tvWeight.text = weight.weight.formatWeight(binding.root.context)
        tvDate.text = weight.date
        handleSelected(isSelected)
    }

    private fun handleSelected(isSelected: Boolean) {
        val activeColor = binding.root.context.getColor(R.color.active)
        if (isSelected) {
            binding.root.setBackgroundColor(activeColor)
            ivIcon.hideWithAnim()
            ivDelete.showWithAnim()
        } else {
            binding.root.setBackgroundColor(Color.TRANSPARENT)
            ivIcon.showWithAnim()
            ivDelete.hideWithAnim()
        }
    }

    private fun handleWeightClick(onWeightClick: () -> Unit) {
        binding.root.setOnClickListener {
            onWeightClick()
        }
    }

    private fun handleDeleteClick(weight: Weight) {
        binding.ivDelete.setOnClickListener {
            onDeleteClick(weight)
        }
    }

    private fun handleLongClick(weight: Weight) {
        binding.root.setOnLongClickListener {
            onLongClick(weight)
            true
        }
    }
}