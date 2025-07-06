package com.yusufcanmercan.weight_track_app.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.core.FragmentConstants
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.FragmentHomeBinding
import com.yusufcanmercan.weight_track_app.ui.adapter.weight.WeightAdapter
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import com.yusufcanmercan.weight_track_app.ui.view.activity.main.MainActivity
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import com.yusufcanmercan.weight_track_app.util.helper.showSnackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var etEmpty: MaterialTextView

    private lateinit var navController: NavController

    private var adapter: WeightAdapter? = null

    private val weightViewModel: WeightViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindVariables()
        bindViews()
        bindEvents()
        observeData()
        setupDeleteResultListener()
    }

    private fun bindVariables() {
        navController = findNavController()
    }

    private fun bindViews() {
        progressBar = binding.progressBar
        recyclerView = binding.recyclerView
        etEmpty = binding.etEmpty
    }

    private fun bindEvents() {
        etEmpty.setOnClickListener {
            etEmpty.isEnabled = false
            val activity = activity as MainActivity
            val direction =
                HomeFragmentDirections.actionHomeFragmentToAddFragment(activity.selectedDate)
            navController.navigate(direction)
        }

        binding.root.setOnClickListener {
            adapter?.resetSelection()
        }
    }

    private fun observeData() {
        parentFragmentManager.setFragmentResultListener(
            FragmentConstants.ADD_FRAGMENT_RESULT_KEY,
            viewLifecycleOwner,
        ) { _, bundle ->
            etEmpty.isEnabled = true
        }

        lifecycleScope.launch {
            weightViewModel.weights.collect {
                when (it) {
                    is WeightUIState.Loading -> loadingLogic()
                    is WeightUIState.Success -> successLogic(it.weights)
                    is WeightUIState.Error -> errorLogic(it.message)
                }
            }
        }
    }

    private fun loadingLogic() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        etEmpty.visibility = View.GONE
    }

    private fun successLogic(weights: List<Weight>) {
        progressBar.visibility = View.GONE

        if (weights.isEmpty()) {
            etEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            etEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        adapter = WeightAdapter(weights.reversed().toMutableList(), ::onEditClick)
        recyclerView.adapter = adapter
    }

    private fun errorLogic(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        etEmpty.visibility = View.VISIBLE

        etEmpty.text = message
    }

    private fun onEditClick(weight: Weight) {
        val direction = HomeFragmentDirections.actionHomeFragmentToEditFragment(weight)
        findNavController().navigate(direction)
    }

    private fun setupDeleteResultListener() {
        parentFragmentManager.setFragmentResultListener(
            FragmentConstants.DELETE_REQUEST_KEY,
            viewLifecycleOwner,
        ) { _, bundle ->
            val weightId = bundle.getLong(FragmentConstants.WEIGHT_ID_KEY)
            val weight = adapter?.getItemById(weightId)
            weightViewModel.deleteWeight(weight!!)
            showSnackbar(binding.root, getString(R.string.delete_completed))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}