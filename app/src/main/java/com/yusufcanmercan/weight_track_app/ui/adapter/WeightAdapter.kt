package com.yusufcanmercan.weight_track_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.WeightListItemBinding

class WeightAdapter(
    private val weights: List<Weight>
) : RecyclerView.Adapter<WeightViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val binding = WeightListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return WeightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        val weight = weights[position]
        holder.bind(weight)
    }

    override fun getItemCount() = weights.size
}