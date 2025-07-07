package com.yusufcanmercan.weight_track_app.ui.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.materialswitch.MaterialSwitch
import com.yusufcanmercan.weight_track_app.databinding.FragmentSettingsBinding
import com.yusufcanmercan.weight_track_app.databinding.ItemDarkModeBinding
import com.yusufcanmercan.weight_track_app.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemDarkMode: ItemDarkModeBinding
    private lateinit var switchDarkMode: MaterialSwitch

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindEvents()
        observeData()
    }

    private fun bindViews() {
        itemDarkMode = binding.itemDarkMode
        switchDarkMode = binding.itemDarkMode.switchDarkMode
    }

    private fun bindEvents() {
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setDarkMode(isChecked)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.isDarkMode.collectLatest { isDarkMode ->
                switchDarkMode.isChecked = isDarkMode
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}