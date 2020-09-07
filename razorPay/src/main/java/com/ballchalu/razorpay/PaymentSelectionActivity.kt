package com.ballchalu.razorpay

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ballchalu.razorpay.base.BaseActivity
import com.ballchalu.razorpay.checkout.result.PaymentFailedDialog
import com.ballchalu.razorpay.checkout.result.PaymentSucessDialog
import com.ballchalu.razorpay.databinding.ActivityPayemtSelectionBinding
import com.razorpay.Razorpay

class PaymentSelectionActivity : BaseActivity() {
    var errorDesc: String? = null
    private lateinit var binding: ActivityPayemtSelectionBinding
    val razorPay: Razorpay? by lazy { Razorpay(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayemtSelectionBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@PaymentSelectionActivity

        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_payment_host)
        NavigationUI.setupWithNavController(binding.toolbar, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbar.isVisible = destination.id != R.id.razorPayViewFragment
        }

    }

    fun getError(desc: String) {
        errorDesc = desc

    }

    fun onPaymentFailure(data: String?) {

        /*  data?.let {
              val error = it.getStringExtra(Constants.ERROR_CODE)
              val message = it.getStringExtra(Constants.ERROR_MESSAGE)
              Toast.makeText(
                  this,
                  "Error $error  Message $message",
                  Toast.LENGTH_LONG
              ).show()
          }*/
        findNavController(R.id.nav_payment_host).popBackStack()
        data?.let { getError(it) }
        PaymentFailedDialog()
            .show(supportFragmentManager, "PaymentFailedDialog")
    }

    fun onPaymentSuccess() {

        findNavController(R.id.nav_payment_host).popBackStack()
        Toast.makeText(this, "PaymentSuccess", Toast.LENGTH_LONG).show()
        PaymentSucessDialog()
            .show(supportFragmentManager, "PaymentFailedDialog")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }


}



