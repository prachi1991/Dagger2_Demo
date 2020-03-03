package com.ballchalu.ui.navigation

import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ballchalu.R
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivityNavigationBinding
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class NavigationActivity : BaseActivity() {
    private lateinit var viewModel: NavigationViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var navController: NavController
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this


        initNavigationDrawer()
        setObservers()
    }

    private fun initNavigationDrawer() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }

    private fun setObservers() {
        binding.ibMenu.setOnClickListener {
            toggleDrawer()
        }
        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.ivCloseDrawer)
            .setOnClickListener {
                toggleDrawer()
            }
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        } else
            binding.drawerLayout.openDrawer(GravityCompat.END)

    }


}
