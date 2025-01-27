package com.yusufcanmercan.weight_track_app.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.core.Constants
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.WeightListItemBinding
import com.yusufcanmercan.weight_track_app.util.hideWithAnim
import com.yusufcanmercan.weight_track_app.util.showWithAnim

class WeightViewHolder(
    private val binding: WeightListItemBinding,
    private val onEditClick: (Weight) -> Unit,
    private val onDeleteClick: (Weight) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weight: Weight, isSelected: Boolean, onWeightClick: () -> Unit) {
        bindViews(weight, isSelected)
        bindEvents(weight, onWeightClick)
    }

    private fun bindViews(weight: Weight, isSelected: Boolean) {
        val weightString = String.format(Constants.localeEn, "%.2f", weight.weight)
        binding.tvWeight.text = binding.root.context.getString(R.string.weight_str, weightString)
        binding.tvDate.text = weight.date

        binding.root.isSelected = isSelected
        if (isSelected) {
            binding.ivEdit.showWithAnim()
            binding.ivDelete.showWithAnim()
        } else {
            binding.ivEdit.hideWithAnim()
            binding.ivDelete.hideWithAnim()
        }
    }

    private fun bindEvents(weight: Weight, onWeightClick: () -> Unit) {
        handleWeightClick(onWeightClick)
        handleEditClick(weight)
        handleDeleteClick(weight)
    }

    private fun handleWeightClick(onWeightClick: () -> Unit) {
        binding.root.setOnClickListener {
            onWeightClick()
        }
    }

    private fun handleEditClick(weight: Weight) {
        binding.ivEdit.setOnClickListener {
            onEditClick(weight)
        }
    }

    private fun handleDeleteClick(weight: Weight) {
        binding.ivDelete.setOnClickListener {
            onDeleteClick(weight)
        }
    }
}