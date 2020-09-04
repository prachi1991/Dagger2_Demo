package com.ballchalu.razorpay
import android.content.Intent
import androidx.core.view.isVisible
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ballchalu.razorpay.base.BaseActivity
import com.ballchalu.razorpay.databinding.ActivityPayemtSelectionBinding
import com.ballchalu.razorpay.method.card.CardFragment
import com.ballchalu.shared.rxjava.RxBus
import com.ballchalu.shared.rxjava.RxEvent
import com.razorpay.Razorpay
import timber.log.Timber

class PaymentSelectionActivity : BaseActivity() {

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
    fun onPaymentFailure(intent: Intent){
        findNavController(R.id.nav_payment_host).popBackStack()
        Toast.makeText(this,"PaymentFailure",Toast.LENGTH_LONG).show()
    }
    fun onPaymentSuccess(intent: Intent){

        findNavController(R.id.nav_payment_host).popBackStack()
        Toast.makeText(this,"PaymentSuccess",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }


    }



