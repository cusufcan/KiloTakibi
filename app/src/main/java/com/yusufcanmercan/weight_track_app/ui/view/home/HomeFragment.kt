package com.yusufcanmercan.weight_track_app.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.FragmentHomeBinding
import com.yusufcanmercan.weight_track_app.ui.adapter.WeightAdapter
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val weightViewModel: WeightViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            weightViewModel.weights.collect {
                when (it) {
                    is WeightUIState.Loading -> loadingLogic()
                    is WeightUIState.Success -> successLogic(it.weights)
                    is WeightUIState.Error -> errorLogic()
                }
            }
        }
    }

    private fun loadingLogic() {

    }

    private fun successLogic(weights: List<Weight>) {
        val adapter = WeightAdapter(weights)
        recyclerView.adapter = adapter
    }

    private fun errorLogic() {

    }

    private fun bindViews() {
        recyclerView = binding.recyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}