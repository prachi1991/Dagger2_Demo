package com.ballchalu.ui.razorpay.checkout.result


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ballchalu.R
import com.ballchalu.databinding.LayoutPaymentFailedBinding
import com.ballchalu.ui.razorpay.container.PaymentSelectionActivity

private lateinit var binding: LayoutPaymentFailedBinding


class PaymentFailedDialog()  // Empty constructor required for DialogFragment
    : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = LayoutPaymentFailedBinding.inflate(inflater)


        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
        );


        getDialog()?.getWindow()?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
binding.tvPaymentFailed.text=getString(R.string.payment_failed)+" "+(activity as PaymentSelectionActivity).errorDesc
        binding.btnTry.setOnClickListener {
            this.dismiss()
        }
        binding.tvCancel.setOnClickListener{
            this.dismiss()
        }
        dialog?.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE);
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            getDialog()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
