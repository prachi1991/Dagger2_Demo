package com.ballchalu.razorpay.method.banking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ballchalu.razorpay.Constants
import com.ballchalu.razorpay.PaymentSelectionActivity
import com.ballchalu.razorpay.R
import com.ballchalu.razorpay.RazorParser
import com.ballchalu.razorpay.base.BaseFragment
import com.ballchalu.razorpay.databinding.FragmentNetBankingBinding
import com.ballchalu.razorpay.method.banking.adapter.NetBankingAdapter
import com.ballchalu.razorpay.model.PaymentDetailsModel
import com.ballchalu.razorpay.payment_mode.PaymentModeFragment
import com.razorpay.BaseRazorpay
import com.razorpay.Razorpay
import timber.log.Timber

class NetBankingFragment : BaseFragment(), NetBankingAdapter.OnClickListener {

    private var adapter: NetBankingAdapter? = null
    private var model: PaymentDetailsModel? = null
    private var product: String? = null
    val razorPay: Razorpay? by lazy { Razorpay(requireActivity()) }
    private var bankCode = ""
    private lateinit var binding: FragmentNetBankingBinding
    private var razorpay: Razorpay? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNetBankingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@NetBankingFragment
        }

        arguments?.let {
            model = it.getParcelable(Constants.PAYMENT_MODEL)
            product = it.getString(Constants.PRODUCT)
        }
        bankCode = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        razorpay = Razorpay(requireActivity())
        initAdapter()
        initRazor()
    }

    private fun initAdapter() {

        adapter = NetBankingAdapter(this)
        binding.rvBankingList.adapter = adapter
    }

    private fun initRazor() {

    razorPay?.getPaymentMethods(object :
            BaseRazorpay.PaymentMethodsCallback {
            override fun onPaymentMethodsReceived(result: String) {


                val list = RazorParser.inflateLists(result)

                adapter?.setItemList(list)
            }

            override fun onError(error: String) {
                Timber.d("Get Payment error $error")
            }
        })
    }

    private fun onProcessDetails(first: String) {
        model = PaymentDetailsModel()
        model?.apply {
            amount = getAmountFromParent()
            method=Constants.TYPE_BANKING
            email="john@gmail.com"
            contact="8888888888"
            model?.bank = first

        }
        try {
            model?.let {
                it.amount = it.amount?.replace(".", "")
                sendRequest(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendRequest(payload: PaymentDetailsModel) {
        val parentFrag: PaymentModeFragment = this.getParentFragment() as PaymentModeFragment
        parentFrag.callBack(
            Bundle().apply {
                putParcelable(Constants.PAYMENT_MODEL, payload)
            })
    }
    private fun getAmountFromParent(): String? {
        val parentFrag: PaymentModeFragment = this.getParentFragment() as PaymentModeFragment
        return parentFrag.getAmount()
    }
    override fun onClicked(pair: Pair<String, String>) {
        bankCode = pair.first
        onProcessDetails(pair.first)
    }


}
