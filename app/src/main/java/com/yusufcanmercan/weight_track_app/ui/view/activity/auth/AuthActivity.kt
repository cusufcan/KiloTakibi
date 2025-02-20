package com.yusufcanmercan.weight_track_app.ui.view.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.databinding.ActivityAuthBinding
import com.yusufcanmercan.weight_track_app.databinding.CustomToolbarBinding
import com.yusufcanmercan.weight_track_app.ui.view.activity.main.MainActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private lateinit var materialToolbar: CustomToolbarBinding

    private lateinit var btnAuth: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KiloTakibi)
        bindingCodes()
        defaultActivityCodes()
        bindViews()
        bindEvents()
        checkUser()
    }

    private fun bindingCodes() {
        binding = ActivityAuthBinding.inflate(layoutInflater)
    }

    private fun defaultActivityCodes() {
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun bindViews() {
        materialToolbar = binding.toolBar
        setSupportActionBar(materialToolbar.customToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        materialToolbar = binding.toolBar
        btnAuth = binding.btnAuth
    }

    private fun bindEvents() {
        btnAuth.setOnClickListener {
            
        }
    }

    private fun checkUser() {
        if (Firebase.auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}