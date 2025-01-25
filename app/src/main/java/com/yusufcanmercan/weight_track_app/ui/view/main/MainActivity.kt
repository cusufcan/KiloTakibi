package com.yusufcanmercan.weight_track_app.ui.view.main

import android.os.Bundle
import androidx.activity.viewModels
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
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var materialToolbar: CustomToolbarBinding

    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView

    private val weightViewModel: WeightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KiloTakibi)

        weightViewModel.fetchData()

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
                R.id.homeFragment -> fabOnDestinationChangeLogic(true)
                R.id.graphFragment -> fabOnDestinationChangeLogic(false)
            }
        }

        floatingActionButton.setOnClickListener { openAddDialogFragment() }
    }

    private fun fabOnDestinationChangeLogic(enable: Boolean) {
        floatingActionButton.isEnabled = enable
        if (enable) {
            floatingActionButton.show()
        } else {
            floatingActionButton.hide()
        }
    }

    private fun openAddDialogFragment() {
        navHostFragment.navController.navigate(R.id.action_homeFragment_to_addFragment)
    }
}