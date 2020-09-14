package com.ballchalu.ui.razorpay.checkout.result


import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.ballchalu.R
import com.ballchalu.databinding.LayoutPaymentCompletedBinding
import com.ballchalu.ui.razorpay.Constants
import com.ballchalu.ui.razorpay.container.PaymentSelectionActivity
import kotlinx.android.synthetic.main.layout_payment_completed.view.*
import kotlinx.android.synthetic.main.layout_payment_failed.view.*


private lateinit var binding: LayoutPaymentCompletedBinding
class PaymentSucessDialog()  // Empty constructor required for DialogFragment
    : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = LayoutPaymentCompletedBinding.inflate(inflater)
        arguments.let {
           if( it!!.getString(Constants.PAYMENT_RESULT)!!.equals(Constants.PAYMENT_SUCESSS)) {
               binding.layoutFailed.visibility=View.GONE
               binding.rlSucess.visibility=View.VISIBLE
            }
            else{
               binding.layoutFailed.visibility=View.VISIBLE
               binding.rlSucess.visibility=View.GONE
           }

        }
        binding.layoutFailed.tv_payment_failed.text=getString(R.string.payment_failed)+" "+(activity as PaymentSelectionActivity).errorDesc
        binding.layoutFailed.btn_try.setOnClickListener {
            this.dismiss()
        }
        binding.layoutFailed.tv_cancel.setOnClickListener{
            this.dismiss()
        }
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
        );


        getDialog()?.getWindow()?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )

        binding.btnOk.setOnClickListener {
            (activity as PaymentSelectionActivity).setCallBack()
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
