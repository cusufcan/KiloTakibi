package com.yusufcanmercan.weight_track_app.ui.adapter.weight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.WeightListItemBinding

class WeightAdapter(
    private val weights: MutableList<Weight>,
    private val onEditClick: (Weight) -> Unit,
) : RecyclerView.Adapter<WeightViewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val binding = WeightListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return WeightViewHolder(binding, onEditClick)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        val weight = weights[position]
        holder.bind(weight, selectedPosition == position) {
            updateSelection(position)
        }
    }

    override fun getItemCount() = weights.size

    private fun updateSelection(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = if (previousPosition == position) {
            RecyclerView.NO_POSITION
        } else {
            position
        }

        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }
    
    fun getItemById(id: Long): Weight? {
        return weights.find { it.id == id }
    }

    fun resetSelection() {
        val previousPosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(previousPosition)
    }
}
