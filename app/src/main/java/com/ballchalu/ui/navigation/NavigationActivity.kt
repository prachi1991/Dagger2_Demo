package com.ballchalu.ui.navigation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ballchalu.BuildConfig
import com.ballchalu.R
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivityNavigationBinding
import com.ballchalu.ui.login.container.LoginActivity
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.user.UserData
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class NavigationActivity : BaseActivity() {
    private lateinit var viewModel: NavigationViewModel
    @Inject
    lateinit var sharePref: SharedPreferenceStorage
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
        setBuildVersion()

        initNavigationDrawer()
        setObservers()
    }

    private fun initNavigationDrawer() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        binding.navView.apply {
            menu.findItem(R.id.nav_logout).apply {
                setOnMenuItemClickListener {
                    openLogoutDialog()
                    binding.drawerLayout.closeDrawer(GravityCompat.END)
                    true
                }
            }

            menu.findItem(R.id.nav_bc_coins).apply {
                setOnMenuItemClickListener {
                    navController.navigate(R.id.nav_bc_coins)
                    toggleDrawer()
                    true
                }
            }
            menu.findItem(R.id.nav_declared).apply {
                setOnMenuItemClickListener {
                    navController.navigate(R.id.nav_declared)
                    toggleDrawer()
                    true
                }
            }
        }

    }

    private fun setHeaderUi(userRes: UserData?) {
        val headerView = binding.navView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.tvNavEmail).text = userRes?.email
        headerView.findViewById<TextView>(R.id.tvMoneyValue).text =
            userRes?.bc_coins?.toString()
    }

    private fun openLogoutDialog() {
        val builder = AlertDialog.Builder(this@NavigationActivity)
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.callLogout()
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            //    alertDialog.dismiss()
        }
        val alertDialog: AlertDialog
        // Create the AlertDialog
        alertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun setObservers() {
        binding.ibMenu.setOnClickListener {
            toggleDrawer()
        }
        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.ivCloseDrawer)
            .setOnClickListener {
                toggleDrawer()
            }

        viewModel.logoutResult.observe(this, EventObserver {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        viewModel.userDetails.observe(this, EventObserver {
            setHeaderUi(it)
        })
        viewModel.callUserDetails()
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        } else
            binding.drawerLayout.openDrawer(GravityCompat.END)
    }

    private fun setBuildVersion() {
        binding.tvBuildNo.text = resources.getString(R.string.build_d, BuildConfig.VERSION_CODE)
        binding.tvVersionCode.text =
            resources.getString(R.string.version_s, BuildConfig.VERSION_NAME)
        binding.tvEnvironment.text = BuildConfig.ENVIRONMENT

    }
}
