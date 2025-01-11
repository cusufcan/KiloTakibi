package com.yusufcanmercan.weight_track_app.ui.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.databinding.ActivityMainBinding
import com.yusufcanmercan.weight_track_app.databinding.CustomToolbarBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var materialToolbar: CustomToolbarBinding

    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KiloTakibi)

        bindingCodes()
        defaultActivityCodes()
        bindViews()
        bindEvents()
    }

    private fun bindingCodes() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun defaultActivityCodes() {
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun bindViews() {
        materialToolbar = binding.toolBar
        setSupportActionBar(materialToolbar.customToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        fragmentContainerView = binding.fragmentContainerView
        navHostFragment = supportFragmentManager.findFragmentById(
            fragmentContainerView.id
        ) as NavHostFragment

        floatingActionButton = binding.floatingActionButton
        bottomNavigationView = binding.bottomNavigationView
    }

    private fun bindEvents() {
        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> floatingActionButton.show()
                R.id.graphFragment -> floatingActionButton.hide()
            }
        }
    }
}