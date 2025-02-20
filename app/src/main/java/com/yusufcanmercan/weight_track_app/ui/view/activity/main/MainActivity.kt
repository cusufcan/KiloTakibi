package com.yusufcanmercan.weight_track_app.ui.view.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.core.Constants
import com.yusufcanmercan.weight_track_app.databinding.ActivityMainBinding
import com.yusufcanmercan.weight_track_app.databinding.CustomToolbarBinding
import com.yusufcanmercan.weight_track_app.databinding.MainCardBinding
import com.yusufcanmercan.weight_track_app.ui.view.home.HomeFragmentDirections
import com.yusufcanmercan.weight_track_app.ui.viewmodel.WeightViewModel
import com.yusufcanmercan.weight_track_app.util.helper.formatWeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var materialToolbar: CustomToolbarBinding
    private lateinit var mainCard: MainCardBinding

    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView

    lateinit var selectedDate: String

    private val weightViewModel: WeightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KiloTakibi)
        bindingCodes()
        defaultActivityCodes()
        bindVariables()
        bindViews()
        bindEvents()
        fetchData()
        observeData()
    }

    private fun bindingCodes() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun defaultActivityCodes() {
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun bindVariables() {
        val calendar = Calendar.getInstance()
        selectedDate = Constants.formatter.format(calendar.time)
    }

    private fun bindViews() {
        materialToolbar = binding.toolBar
        setSupportActionBar(materialToolbar.customToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mainCard = binding.mainCard

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

    private fun fetchData() {
        weightViewModel.fetchData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            weightViewModel.weightStat.collect {
                val context = mainCard.root.context
                mainCard.tvCurrent.text = it.current.formatWeight(context)
                mainCard.tvChange.text = it.change.formatWeight(context)
                mainCard.tvWeekly.text = it.weekly.formatWeight(context)
                mainCard.tvMonthly.text = it.monthly.formatWeight(context)
            }
        }
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
        val direction = HomeFragmentDirections.actionHomeFragmentToAddFragment(selectedDate)
        navHostFragment.navController.navigate(direction)
    }
}