package com.yusufcanmercan.weight_track_app.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.WeightListItemBinding
import java.util.Locale

class WeightViewHolder(
    private val binding: WeightListItemBinding,
    private val onWeightLongClick: (Weight) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weight: Weight) {
        val weightString = String.format(Locale("tr", "TR"), "%.2f", weight.weight)

        binding.tvWeight.text = binding.root.context.getString(R.string.weight_str, weightString)
        binding.tvDate.text = weight.date

        binding.root.setOnLongClickListener {
            onWeightLongClick(weight)
            true
        }
    }
}