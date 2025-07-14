package com.yusufcanmercan.weight_track_app.ui.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.materialswitch.MaterialSwitch
import com.yusufcanmercan.weight_track_app.databinding.FragmentSettingsBinding
import com.yusufcanmercan.weight_track_app.databinding.ItemDarkModeBinding
import com.yusufcanmercan.weight_track_app.databinding.ItemLanguageBinding
import com.yusufcanmercan.weight_track_app.ui.viewmodel.SettingsViewModel
import com.yusufcanmercan.weight_track_app.util.helper.convertToISO639
import com.yusufcanmercan.weight_track_app.util.helper.convertToLanguage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemDarkMode: ItemDarkModeBinding
    private lateinit var switchDarkMode: MaterialSwitch
    private lateinit var itemLanguage: ItemLanguageBinding
    private lateinit var spinnerLanguage: Spinner

    private lateinit var languages: MutableList<String>
    private lateinit var languageAdapter: ArrayAdapter<String>

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    private var isSpinnerInitialized = false

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
        bindLanguageMenu()
        bindEvents()
        observeData()
    }

    private fun bindViews() {
        itemDarkMode = binding.itemDarkMode
        switchDarkMode = binding.itemDarkMode.switchDarkMode
        itemLanguage = binding.itemLanguage
        spinnerLanguage = binding.itemLanguage.spinnerLanguage
    }

    private fun bindLanguageMenu() {
        languages = mutableListOf("Türkçe", "English")
        languageAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            languages,
        )
        spinnerLanguage.adapter = languageAdapter
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

        spinnerLanguage.onItemSelectedListener = this
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.isDarkMode.collectLatest { isDarkMode ->
                switchDarkMode.isChecked = isDarkMode
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.selectedLanguage.collectLatest { selectedLanguage ->
                if (selectedLanguage == null) return@collectLatest

                spinnerLanguage.setSelection(languages.indexOf(selectedLanguage.convertToLanguage()))

                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(selectedLanguage)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        long: Long,
    ) {
        if (!isSpinnerInitialized) {
            isSpinnerInitialized = true
            return
        }

        val selectedLanguage = adapterView?.getItemAtPosition(position).toString()
        settingsViewModel.setLanguage(selectedLanguage.convertToISO639())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}