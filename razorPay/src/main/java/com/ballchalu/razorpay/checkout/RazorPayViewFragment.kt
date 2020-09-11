package com.ballchalu.razorpay.checkout

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.razorpay.Constants
import com.ballchalu.razorpay.container.PaymentSelectionActivity
import com.ballchalu.razorpay.base.BaseFragment
import com.ballchalu.razorpay.checkout.model.RazorPayError
import com.ballchalu.razorpay.databinding.FragmentRazorPayViewBinding
import com.ballchalu.razorpay.model.PaymentDetailsModel
import com.ballchalu.razorpay.showMessageDialog
import com.google.gson.Gson
import com.razorpay.BaseRazorpay
import com.razorpay.PaymentResultListener
import com.razorpay.Razorpay
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
class RazorPayViewFragment : BaseFragment(), PaymentResultListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentRazorPayViewBinding
    private var razorpay: Razorpay? = null
    private var amount: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRazorPayViewBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@RazorPayViewFragment
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().showMessageDialog(DialogInterface.OnClickListener { p0, p1 ->
                        razorpay?.onBackPressed()
                        findNavController().popBackStack()
                    })
                }
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        razorpay = Razorpay(requireActivity())
        razorpay?.setWebView(binding.webView)


        arguments?.let {
            try {
                val model = it.getParcelable<PaymentDetailsModel>(Constants.PAYMENT_MODEL)
                amount = model?.amount
                val json = JSONObject(Gson().toJson(model))

                Timber.d("RazorPayCallback ${model?.amount.toString()}")
//                razorpay?.openCheckout(json,this)
                sendRequest(json)
            } catch (e: Exception) {
                Timber.d("Exception123 ${e.message}")
                Toast.makeText(requireActivity(), "Data Parsing Error", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }


    private fun sendRequest(payload: JSONObject) {
        razorpay?.validateFields(payload, object : BaseRazorpay.ValidationListener {
            override fun onValidationSuccess() {
                razorpay?.submit(payload, this@RazorPayViewFragment)
            }

            override fun onValidationError(error: Map<String, String>) {
                Toast.makeText(
                    requireContext(),
                    "Validation: " + error["field"] + " " + error["description"],
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        razorpay?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPaymentError(errorCode: Int, errorDescription: String) {
        var error: RazorPayError? = null
        Timber.d("onPaymentError ${error}")
        try {
            error = Gson().fromJson(errorDescription, RazorPayError::class.java)
        } catch (e: Exception) {
            Timber.d("onPaymentError ${e.message}")
        }
        val intent = Intent().apply {
            putExtra(Constants.ERROR_CODE, errorCode.toString())
            putExtra(Constants.ERROR_MESSAGE, error?.error?.description ?: errorDescription)
        }
        requireActivity().setResult(Activity.RESULT_CANCELED, intent)
        (requireActivity() as PaymentSelectionActivity).onPaymentFailure(error?.error?.description)
    }

    override fun onPaymentSuccess(razorpayPaymentId: String) {
        val intent = requireActivity().intent
        Timber.d("OnPaymentSuccess ${razorpayPaymentId}  ${amount}")
        intent.putExtra(Constants.RAZOR_PAY_ID, razorpayPaymentId)
        intent.putExtra(Constants.AMOUNT, amount)
        requireActivity().setResult(Activity.RESULT_OK, intent)

        (requireActivity() as PaymentSelectionActivity).onPaymentSuccess()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        razorpay?.onActivityResult(requestCode, resultCode, data)
    }
}
