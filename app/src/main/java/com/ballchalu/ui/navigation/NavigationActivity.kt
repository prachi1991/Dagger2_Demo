package com.ballchalu.ui.navigation

import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
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
import com.ballchalu.utils.ThemeHelper
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.user.UserData
import com.ccpp.shared.rxjava.RxBus
import com.ccpp.shared.rxjava.RxEvent
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class NavigationActivity : BaseActivity() {
    private var disposable: Disposable? = null
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
        setRxObserver()
        initNavigationDrawer()
        setObservers()
        registerReceiver()
    }

    private fun setRxObserver() {
        disposable = RxBus.listen(Any::class.java).subscribe { event ->
            when (event) {
                ConstantsBase.TOKEN_EXPIRED -> viewModel.callLogout()
                is RxEvent.BcCoin -> updateContestCoin(event)
            }
        }
    }

    private fun updateContestCoin(it: RxEvent.BcCoin) {
        binding.tvContestCoin.text = getString(R.string.bc_coin_s, it.userContest.availableCoins)
        setHeaderUi(it.userContest.user)
    }

    private fun initNavigationDrawer() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.tvContestCoin.text = ""
            binding.tvContestCoin.visibility =
                if (destination.id == R.id.nav_home_match_details) View.VISIBLE else View.GONE

        }
        binding.navView.apply {
            menu.findItem(R.id.nav_logout).apply {
                setOnMenuItemClickListener {
                    openLogoutDialog()
                    binding.drawerLayout.closeDrawer(GravityCompat.END)
                    true
                }
            }
            menu.findItem(R.id.nav_theme).apply {
                setOnMenuItemClickListener {
                    openThemeSelection()
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
        userRes?.let {
            val headerView = binding.navView.getHeaderView(0)
            headerView.findViewById<TextView>(R.id.tvNavEmail).text = it.user_name
            headerView.findViewById<TextView>(R.id.tvMoneyValue).text = it.bc_coins?.toString()
        }
    }

    private fun openLogoutDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
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
        builder.show()

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
        if (BuildConfig.DEBUG) {
            binding.tvEnvironment.text = BuildConfig.ENVIRONMENT
            binding.tvEnvironment.visibility = View.VISIBLE
        }

    }


    private fun registerReceiver() {
        registerReceiver(viewModel.declareEvent, IntentFilter(ConstantsBase.MATCH_DECLARED))
    }

    private fun unRegisterReceiver() {
        unregisterReceiver(viewModel.declareEvent)
    }

    override fun onDestroy() {
        disposable?.dispose()
        unRegisterReceiver()
        super.onDestroy()
    }

    private fun openThemeSelection() {
        val values = arrayOf<CharSequence>(
            ConstantsBase.THEME_DARK,
            ConstantsBase.THEME_LIGHT,
            ConstantsBase.THEME_DEFAULT
        )
        val builder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        builder.setTitle("Select theme")
        builder.setSingleChoiceItems(
            values, viewModel.getSelectedTheme()
        ) { dialog, item ->
            sharePref.theme = ThemeHelper.getSelectedTheme(item)
        }
        builder.show()
    }
}
