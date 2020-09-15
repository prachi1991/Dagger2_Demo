package com.ballchalu.ui.razorpay.method.card

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.databinding.FragmentCardBinding
import com.ballchalu.ui.razorpay.Constants
import com.ballchalu.ui.razorpay.base.BaseFragment
import com.ballchalu.ui.razorpay.container.PaymentSelectionActivity
import com.ballchalu.ui.razorpay.model.PaymentDetailsModel
import com.ballchalu.ui.razorpay.payment_mode.PaymentModeFragment
import com.razorpay.Razorpay

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class CardFragment(price: String?) : BaseFragment() {

    private var model: PaymentDetailsModel? = null
    private var current = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentCardBinding
    private lateinit var viewModel: CardViewModel
    private var razorpay: Razorpay? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CardFragment
        }

        /* arguments?.let {
             model = it.getParcelable(Constants.PAYMENT_MODEL)
             product = it.getString(Constants.PRODUCT)
         }*/

        /*  binding.tvAmount.text = "${model?.amount}"
          binding.tvProduct.text = "$product"*/
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        razorpay = Razorpay(requireActivity())

        binding.btnPayByCard.setOnClickListener {
            hideSoftKeyBoard()
            if (checkButton())
                onProcessDetails()
        }
        binding.edtCardNumber.doAfterTextChanged {
            it?.let { it1 -> formatCardNumber(it1) }
            //  checkButton()

        }

        binding.edtExpiryDate.doAfterTextChanged {
            it?.let { it1 -> formatDate(it1) }

        }
        binding.checkbox.setOnClickListener {
            hideSoftKeyBoard()
        }
        binding.edtHolderName.doAfterTextChanged {


        }

    }

    fun formatDate(number: Editable) {
        if (number.toString() != current) {
            val userInput = number.toString().replace(nonDigits, "")
            if (userInput.length <= 4) {
                current = userInput.chunked(2).joinToString("/")
                number?.filters = arrayOfNulls<InputFilter>(0)
            }
            number?.length?.let { it1 -> number.replace(0, it1, current, 0, current.length) }
        }
    }

    fun formatCardNumber(number: Editable) {
        if (number.toString() != current) {
            val userInput = number.toString().replace(nonDigits, "")
            if (userInput.length <= 16) {
                current = userInput.chunked(4).joinToString(" ")
                number?.filters = arrayOfNulls<InputFilter>(0)
            }
            number?.length?.let { it1 -> number.replace(0, it1, current, 0, current.length) }
        }
    }

    companion object {
        private val nonDigits = Regex("[^\\d]")
    }

    private fun onProcessDetails() {


        model = PaymentDetailsModel()
        model?.apply {
            cardname = binding.edtHolderName.text.toString().trim()
            cardnumber = binding.edtCardNumber.text.toString().replace("\\s".toRegex(), "")
            cardexpiryMonth = binding.edtExpiryDate.text.toString().substring(0, 2)
            cardexpiryYear = binding.edtExpiryDate.text.toString().substring(3)
            cardcvv = binding.edtCvv.text.toString().trim()
            amount = getAmountFromParent()
            method = Constants.TYPE_CARD
            email = (activity as PaymentSelectionActivity).userdata.email
            contact = "8888888888"

        }
        try {
            model?.let {


                sendRequest(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAmountFromParent(): String? {
        val parentFrag: PaymentModeFragment = this.getParentFragment() as PaymentModeFragment
        return parentFrag.getAmount()
    }

    private fun sendRequest(payload: PaymentDetailsModel) {


        val parentFrag: PaymentModeFragment = this.getParentFragment() as PaymentModeFragment
        parentFrag.callBack(
            Bundle().apply {
                putParcelable(Constants.PAYMENT_MODEL, payload)
            })


    }

    fun ValidateExpiryDate(): Boolean {
        if (binding.edtExpiryDate.text.length == 5) {
            val sdf = SimpleDateFormat("MM/yy")
            val strDate = sdf.parse(binding?.edtExpiryDate?.text?.toString())
            return (Date().before(strDate))
        }
        return false
    }

    private fun checkButton(): Boolean {


        var enable = true
        if (binding.edtCardNumber.text.toString().isEmpty()) {
            enable = false
            binding.edtCardNumber.error = getString(R.string.validation_cardnumber)
        }

        if (binding.edtExpiryDate.text.toString().isEmpty()) {

            enable = false
            binding.edtExpiryDate.error = getString(R.string.validation_expirydate)
        }
        if (!ValidateExpiryDate()) {
            enable = false
            binding.edtExpiryDate.error = getString(R.string.validation_expirydate)
        }
        if (binding.edtHolderName.text.toString().isEmpty()) {
            binding.edtHolderName.error = getString(R.string.validation_cardholder)
            enable = false
        }
        if (!(binding.edtCvv.text.toString().length == 3)) {
            binding.edtCvv.error = getString(R.string.validation_cvv)
            enable = false
        }
        if (!binding.checkbox.isChecked) {
            binding.checkbox.error = getString(R.string.checkbox)
            enable = false
        }
        return enable


    }

}


