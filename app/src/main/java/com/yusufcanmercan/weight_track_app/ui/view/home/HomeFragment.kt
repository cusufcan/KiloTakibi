package com.yusufcanmercan.weight_track_app.ui.view.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.FragmentHomeBinding
import com.yusufcanmercan.weight_track_app.ui.adapter.WeightAdapter
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import com.yusufcanmercan.weight_track_app.ui.view.main.MainActivity
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var etEmpty: MaterialTextView

    private lateinit var navController: NavController

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
            navController.navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun observeData() {
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

        changeGravity(Gravity.CENTER)
    }

    private fun successLogic(weights: List<Weight>) {
        progressBar.visibility = View.GONE

        if (weights.isEmpty()) {
            etEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            changeGravity(Gravity.CENTER)
        } else {
            etEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            changeGravity(Gravity.TOP)
        }

        val adapter = WeightAdapter(weights)
        recyclerView.adapter = adapter
    }

    private fun errorLogic(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        etEmpty.visibility = View.VISIBLE

        etEmpty.text = message

        changeGravity(Gravity.CENTER)
    }

    private fun changeGravity(gravity: Int) {
        val mainActivity = activity as MainActivity
        val fragmentContainerView = mainActivity.fragmentContainerView
        val layoutParams = fragmentContainerView.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = gravity
        fragmentContainerView.layoutParams = layoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}