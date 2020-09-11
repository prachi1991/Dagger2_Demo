package com.ballchalu.ui.navigation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ballchalu.BuildConfig
import com.ballchalu.R
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivityNavigationBinding
import com.ballchalu.razorpay.Constants
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes
import com.ballchalu.shared.domain.contest.UserContest
import com.ballchalu.shared.domain.user.UserData
import com.ballchalu.shared.rxjava.RxBus
import com.ballchalu.shared.rxjava.RxEvent
import com.ballchalu.shared.util.ConstantsBase
import com.ballchalu.shared.util.loadImage
import com.ballchalu.shared.util.viewModelProvider
import com.ballchalu.ui.login.container.LoginActivity
import com.ballchalu.utils.ThemeHelper
import com.ballchalu.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.reflect.Field
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
                is RxEvent.BcCoin ->
                    when (event.action) {
                        Constants.PAYMENT_SUCESSS -> {

                            setHeaderUi((event.userContest as BcCoinBuyRes).bc_coins_transaction?.user)
                        }
                        Constants.MATCH_DETAILS -> {
                            updateContestCoin(event.userContest as UserContest)
                        }
                    }

                is RxEvent.UpdateProfile -> {
                    loadImage(event.bitmap, binding.ibProfile)
                }

            }
        }
    }

    private fun updateContestCoin(userContest: UserContest) {
        userContest.availableCoins?.let {
            binding.tvContestCoin.text = if (it.isEmpty()) "" else getString(R.string.bc_coin_s, it)
        }
        setHeaderUi(userContest.user)
    }

    private fun initNavigationDrawer() {

        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        binding.toolbar.setNavigationIcon(R.drawable.ic_humburger)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.tvContestCoin.visibility =
                if (destination.id == R.id.nav_home_match_details) View.VISIBLE else View.GONE
            closeDrawer()
            if (destination.id in ids) {
                binding.toolbar.setNavigationIcon(R.drawable.ic_humburger)
            }
        }
        binding.navView.apply {

            menu.findItem(R.id.nav_bc_coins).apply {
                setOnMenuItemClickListener {
                    navigate(R.id.nav_bc_coins)
                    true
                }
            }
            menu.findItem(R.id.nav_home).apply {
                setOnMenuItemClickListener {
                    navigate(R.id.nav_home)
                    true
                }
            }
            menu.findItem(R.id.nav_coin_ledgers).apply {
                setOnMenuItemClickListener {
                    navigate(R.id.nav_coin_ledgers)
                    true
                }
            }
            menu.findItem(R.id.nav_declared).apply {
                setOnMenuItemClickListener {
                    navigate(R.id.nav_declared)
                    true
                }
            }
            menu.findItem(R.id.nav_how_to_play).apply {
                setOnMenuItemClickListener {
                    navigate(R.id.nav_how_to_play)
                    true
                }
            }


//            menu.findItem(R.id.nav_logout).apply {
//                setOnMenuItemClickListener {
//                    openLogoutDialog()
//                    closeDrawer()
//                    true
//                }
//            }
            menu.findItem(R.id.nav_theme).apply {
                setOnMenuItemClickListener {
                    openThemeSelection()
                    closeDrawer()
                    true
                }
            }
        }

    }

    private fun navigate(id: Int) {
        if (navController.currentDestination?.id != id)
            navController.navigate(id)
        closeDrawer()
    }

    private fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun setHeaderUi(userRes: UserData?) {
        userRes?.let {
            val headerView = binding.navView.getHeaderView(0)
            headerView.findViewById<TextView>(R.id.tvNavEmail).text = it.user_name
            headerView.findViewById<TextView>(R.id.tvMoneyValue).text = it.bc_coins?.toString()
            if (it.profileUrl.isNullOrEmpty()) {
                val image = Utils.getDrawableToBitmap(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_user
                    )!!
                )
                loadImage(image, binding.ibProfile)
            } else
                loadImage(it.profileUrl!!, binding.ibProfile)
        }
    }

    private fun openLogoutDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        builder.setTitle(R.string.dialogTitle)
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.callLogout()
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        builder.show()

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }


    private fun setObservers() {
//        binding.toolbar.setNavigationOnClickListener {
//            toggleDrawer()
//        }
        binding.ibProfile.setOnClickListener {
            navController.navigate(R.id.nav_profile_list)
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else
            binding.drawerLayout.openDrawer(GravityCompat.START)
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
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Select theme")
        builder.setSingleChoiceItems(
            values, viewModel.getSelectedTheme()
        ) { dialog, item ->
            dialog.dismiss()
            sharePref.theme = ThemeHelper.getSelectedTheme(item)

        }
        builder.show()
    }

    private fun initProfileMenu(view: View) {
        val wrapper = ContextThemeWrapper(this, R.style.MyPopupOtherStyle)
        val popup = PopupMenu(wrapper, view)
        try {
            val fields: Array<Field> = popup.javaClass.declaredFields
            for (field in fields) {
                if ("mPopup" == field.name) {
                    field.isAccessible = true
                    val menuPopupHelper = field.get(popup)
                    val setForceIcons = Class.forName(menuPopupHelper.javaClass.name).getMethod(
                        "setForceShowIcon",
                        Boolean::class.javaPrimitiveType
                    )
                    setForceIcons.invoke(menuPopupHelper, true)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        popup.menuInflater.inflate(R.menu.menu_profile, popup.menu)
        popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.menuProfile -> {
                        if (navController.currentDestination?.id != R.id.nav_profile_list)
                            navController.navigate(R.id.nav_profile_list)
                    }
                    R.id.menuChangePassword -> {
                        if (navController.currentDestination?.id != R.id.nav_changePassword)
                            navController.navigate(R.id.nav_changePassword)
                    }
                    R.id.menuLogout -> {
                        openLogoutDialog()
                    }
                }

                return true
            }

        })

        popup.show() //showing popup menu

    }

    private val appBarConfiguration by lazy { AppBarConfiguration(ids, binding.drawerLayout) }
    private val ids = setOf(
        R.id.nav_home,
        R.id.nav_coin_ledgers,
        R.id.nav_declared,
        R.id.nav_how_to_play,
        R.id.nav_bc_coins

    )

}
