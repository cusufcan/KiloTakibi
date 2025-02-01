package com.yusufcanmercan.weight_track_app.ui.view.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.core.Constants
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.FragmentEditBinding
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class EditFragment : DialogFragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var weight: Weight

    private lateinit var tvBtnClose: MaterialTextView
    private lateinit var etWeight: TextInputEditText
    private lateinit var btnPickDate: MaterialButton
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnOk: MaterialButton

    private lateinit var calendar: Calendar

    private val weightViewModel: WeightViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindWeight()
        bindCalendar()
        bindViews()
        bindValues()
        bindEvents()
    }

    private fun bindWeight() {
        val args = EditFragmentArgs.fromBundle(requireArguments())
        weight = args.weight
    }

    private fun bindCalendar() {
        calendar = Calendar.getInstance()
    }

    private fun bindViews() {
        tvBtnClose = binding.tvBtnClose
        etWeight = binding.etWeight
        btnPickDate = binding.btnPickDate
        btnCancel = binding.btnCancel
        btnOk = binding.btnOk
    }

    private fun bindValues() {
        etWeight.setText(String.format(Constants.localeEn, "%.2f", weight.weight))
        btnPickDate.text = weight.date
        calendar.time = Constants.formatter.parse(weight.date)!!
    }

    private fun bindEvents() {
        tvBtnClose.setOnClickListener { dismissDialog() }
        etWeight.addTextChangedListener { setBtnOkEnabled(!it.isNullOrEmpty()) }
        btnPickDate.setOnClickListener { showDatePicker() }
        btnCancel.setOnClickListener { dismissDialog() }
        btnOk.setOnClickListener { updateDate() }
    }

    private fun dismissDialog() {
        dismiss()
    }

    private fun setBtnOkEnabled(enable: Boolean) {
        btnOk.isEnabled = enable
    }

    private fun showDatePicker() {
        DatePickerDialog(
            requireActivity(),
            { _, year, month, dayOfMonth ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                btnPickDate.text = Constants.formatter.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).apply {
            datePicker.maxDate = Calendar.getInstance().timeInMillis
            show()
        }
    }

    private fun updateDate() {
        val weightNumber = etWeight.text.toString().toDouble()
        val date = btnPickDate.text.toString()

        val newWeight = weight.copy(weight = weightNumber, date = date)
        lifecycleScope.launch {
            val response = weightViewModel.updateWeight(newWeight)
            if (response) {
                dismissDialog()
            } else {
                etWeight.error = getString(R.string.error_weight_already_exist)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}