package com.ballchalu.ui.razorpay.container

import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ballchalu.R
import com.ballchalu.databinding.ActivityPayemtSelectionBinding
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.domain.bccoins.BcCoinContest
import com.ballchalu.shared.rxjava.RxBus
import com.ballchalu.shared.rxjava.RxEvent
import com.ballchalu.shared.util.loadImage
import com.ballchalu.shared.util.viewModelProvider
import com.ballchalu.ui.razorpay.Constants
import com.ballchalu.ui.razorpay.checkout.result.PaymentFailedDialog
import com.ballchalu.ui.razorpay.checkout.result.PaymentSucessDialog
import com.razorpay.Razorpay
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class PaymentSelectionActivity : com.ballchalu.base.BaseActivity() {
    var errorDesc: String? = null
    private lateinit var binding: ActivityPayemtSelectionBinding
    val razorPay: Razorpay? by lazy { Razorpay(this) }
    private lateinit var navController: NavController
    private lateinit var viewModel: PaymentSelectionViewModel
    private var disposable: Disposable? = null

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
        binding.ibProfile.setOnClickListener {
            navController.navigate(R.id.nav_profile_list)
        }
        viewModel.callUserDetails()
    }


    fun getError(desc: String) {
        errorDesc = desc

    }

    override fun onStart() {
        super.onStart()
        viewModel.bcCoinBuyObserver.observe(this, EventObserver {
            it.bc_coins_transaction?.user?.profileUrl?.let { it1 ->
                loadImage(
                    it1,
                    binding.ibProfile
                )
            }
            RxBus.publish(RxEvent.BcCoin(it, Constants.PAYMENT_SUCESSS))

        })
        viewModel.userDetails.observe(this, EventObserver {
            it?.profileUrl?.let { it1 ->
                loadImage(
                    it1,
                    binding.ibProfile
                )
            }
        });
    }

    fun getBcCoinData() {
        val model = intent.getParcelableExtra<BcCoinContest>(Constants.BC_COINS_ID)

        viewModel.bccoin = model
    }

    fun setCallBack() {


    }

    fun getBackBtn(): ImageView {
        return binding.imgBackbtn
    }

/*    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }*/

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
        disposable?.dispose()
        binding.unbind()
    }


}



