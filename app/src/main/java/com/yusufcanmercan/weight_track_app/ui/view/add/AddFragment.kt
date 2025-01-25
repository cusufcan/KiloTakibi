package com.yusufcanmercan.weight_track_app.ui.view.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.databinding.FragmentAddBinding
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import com.yusufcanmercan.weight_track_app.util.today
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
    private lateinit var formatterShow: SimpleDateFormat

    private val weightViewModel: WeightViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindVariables()
        bindViews()
        bindEvents()
        setTodayDate()
    }

    private fun bindVariables() {
        calendar = Calendar.getInstance()
        formatterShow = SimpleDateFormat("dd MMMM yyyy", Locale("tr", "TR"))
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
        etWeight.addTextChangedListener { setBtnOkEnabled(!it.isNullOrEmpty()) }
        btnPickDate.setOnClickListener { showDatePicker() }
        btnCancel.setOnClickListener { dismissDialog() }
        btnOk.setOnClickListener { saveDate() }
    }

    private fun setTodayDate() {
        btnPickDate.text = today()
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
                btnPickDate.text = formatterShow.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).apply { show() }
    }

    private fun dismissDialog() {
        dismiss()
    }

    private fun saveDate() {
        val weightNumber = etWeight.text.toString().toDouble()
        val date = btnPickDate.text.toString()

        val weight = Weight(weightNumber, date)
        lifecycleScope.launch {
            val response = weightViewModel.addWeight(weight)
            if (response) {
                dismissDialog()
            } else {
                etWeight.error = "Bu tarihte veri zaten eklenmiş"
            }
        }
    }
}