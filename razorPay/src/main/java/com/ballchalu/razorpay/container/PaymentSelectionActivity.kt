package com.ballchalu.razorpay.container

import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ballchalu.razorpay.Constants
import com.ballchalu.razorpay.R
import com.ballchalu.razorpay.base.BaseActivity
import com.ballchalu.razorpay.checkout.result.PaymentFailedDialog
import com.ballchalu.razorpay.checkout.result.PaymentSucessDialog
import com.ballchalu.razorpay.databinding.ActivityPayemtSelectionBinding
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.domain.bccoins.BcCoinContest
import com.ballchalu.shared.domain.contest.UserContest
import com.ballchalu.shared.rxjava.RxBus
import com.ballchalu.shared.rxjava.RxEvent
import com.ballchalu.shared.util.activityViewModelProvider
import com.ballchalu.shared.util.viewModelProvider
import com.razorpay.Razorpay
import timber.log.Timber
import javax.inject.Inject


class PaymentSelectionActivity : BaseActivity() {
    var errorDesc: String? = null
    private lateinit var binding: ActivityPayemtSelectionBinding
    val razorPay: Razorpay? by lazy { Razorpay(this) }
    private lateinit var navController: NavController
    private lateinit var viewModel: PaymentSelectionViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayemtSelectionBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@PaymentSelectionActivity

        }
        viewModel = viewModelProvider(viewModelFactory)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        getBcCoinData()

        navController = findNavController(R.id.nav_payment_host)
        NavigationUI.setupWithNavController(binding.toolbar, navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbar.isVisible = destination.id != R.id.razorPayViewFragment
        }
    }

    fun getError(desc: String) {
        errorDesc = desc

    }

    override fun onStart() {
        super.onStart()
        viewModel.bcCoinBuyObserver.observe(this, EventObserver {

       RxBus.publish(RxEvent.BcCoin(it,Constants.PAYMENT_SUCESSS))

    })
    }
    fun getBcCoinData() {
        val model = intent.getParcelableExtra<BcCoinContest>(Constants.BC_COINS_ID)
        Timber.d("stringExtra ${model.id}")
        viewModel.bccoin = model
    }

    fun setCallBack() {


    }

    fun getBackBtn(): ImageView {
        return binding.imgBackbtn
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    fun onPaymentFailure(data: String?) {


        findNavController(R.id.nav_payment_host).popBackStack()
        data?.let { getError(it) }
        PaymentFailedDialog()
            .show(supportFragmentManager, "PaymentFailedDialog")
    }

    fun onPaymentSuccess() {

        viewModel.callBuyNow(viewModel.bccoin)
        findNavController(R.id.nav_payment_host).popBackStack()
        PaymentSucessDialog()
            .show(supportFragmentManager, "PaymentSucessDialog")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }


}



