package com.yusufcanmercan.weight_track_app.util.view

import android.text.Editable
import android.text.TextWatcher

interface CustomTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}