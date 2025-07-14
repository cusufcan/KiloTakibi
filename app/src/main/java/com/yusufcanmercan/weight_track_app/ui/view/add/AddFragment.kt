package com.yusufcanmercan.weight_track_app.ui.view.add

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.core.FragmentConstants
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.FragmentAddBinding
import com.yusufcanmercan.weight_track_app.ui.view.activity.main.MainActivity
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import com.yusufcanmercan.weight_track_app.util.helper.toDateStr
import com.yusufcanmercan.weight_track_app.util.view.CustomTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvBtnClose: MaterialTextView
    private lateinit var etWeight: TextInputEditText
    private lateinit var btnPickDate: MaterialButton
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnOk: MaterialButton

    private lateinit var calendar: Calendar

    private var selectedDate: Long? = null

    private val weightViewModel: WeightViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindVariables()
        bindEvents()
    }

    private fun bindVariables() {
        selectedDate = arguments?.getLong("selectedDate")
        calendar = Calendar.getInstance()

        selectedDate?.let {
            calendar.timeInMillis = it
        }

        btnPickDate.text = calendar.timeInMillis.toDateStr()
    }

    private fun bindViews() {
        tvBtnClose = binding.tvBtnClose
        etWeight = binding.etWeight
        btnPickDate = binding.btnPickDate
        btnCancel = binding.btnCancel
        btnOk = binding.btnOk
    }

    private fun bindEvents() {
        tvBtnClose.setOnClickListener { dismissDialog() }
        etWeight.addTextChangedListener(object : CustomTextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setBtnOkEnabled(!s.isNullOrEmpty())
            }

            override fun afterTextChanged(s: Editable?) {
                checkInputRegex(s)
            }
        })
        btnPickDate.setOnClickListener { showDatePicker() }
        btnCancel.setOnClickListener { dismissDialog() }
        btnOk.setOnClickListener { saveDate() }
    }

    private fun setBtnOkEnabled(enable: Boolean) {
        btnOk.isEnabled = enable
    }

    private fun checkInputRegex(s: Editable?) {
        val input = s.toString()
        if (input.isEmpty()) return
        if (!input.matches("\\d{0,3}(\\.\\d{0,2})?".toRegex())) {
            val corrected = input.substring(0, input.length - 1)
            etWeight.setText(corrected)
            etWeight.setSelection(corrected.length)
        }
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
                selectedDate = calendar.timeInMillis
                btnPickDate.text = selectedDate?.toDateStr()
                (activity as MainActivity).selectedDate = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).apply {
            datePicker.maxDate = Calendar.getInstance().timeInMillis
            show()
        }
    }

    private fun saveDate() {
        val weightNumber = etWeight.text.toString().toDouble()

        selectedDate?.let {
            val weight = Weight(weight = weightNumber, timeStamp = it)
            lifecycleScope.launch {
                val response = weightViewModel.addWeight(weight)
                if (response) {
                    dismissDialog()
                } else {
                    etWeight.error = getString(R.string.error_weight_already_exist)
                }
            }
        }
    }

    private fun enableHomeButtons() {
        val result = Bundle()
        parentFragmentManager.setFragmentResult(
            FragmentConstants.ADD_FRAGMENT_RESULT_KEY,
            result,
        )
    }

    private fun dismissDialog() {
        enableHomeButtons()
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        enableHomeButtons()
    }
}