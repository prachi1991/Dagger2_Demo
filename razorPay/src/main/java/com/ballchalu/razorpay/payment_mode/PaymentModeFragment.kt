package com.ballchalu.razorpay.payment_mode


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.ballchalu.razorpay.Constants
import com.ballchalu.razorpay.R
import com.ballchalu.razorpay.databinding.FragmentPaymentModeBinding
import com.ballchalu.razorpay.method.banking.NetBankingFragment
import com.ballchalu.razorpay.method.card.CardFragment
import com.ballchalu.razorpay.model.PaymentDetailsModel
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_choose_package.view.*
import kotlinx.android.synthetic.main.layout_payment_option.view.*
import java.util.*
import java.util.regex.Pattern


class PaymentModeFragment : Fragment() {
    private var adapter: PaymentModeFragment.ViewPagerAdapter? = null
    private lateinit var binding: FragmentPaymentModeBinding
    var rotationAngle = 0
    var price: String? = null
    var rotationAnglePayment = 0
    private var disposable: Disposable? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentModeBinding.inflate(inflater)

        binding.layoutPackage.btn5000.setOnClickListener {

            setselection(it)

            price = "5000"
        }
        binding.layoutPackage.btn1000Rs.setOnClickListener {
            price = "1000"
            setselection(it)

        }
        binding.layoutPackage.btn2000Rs.setOnClickListener {
            price = "2000"
            setselection(it)

        }
        binding.layoutPackage.btn3000Rs.setOnClickListener {
            price = "3000"
            setselection(it)


        }

        binding.cardviewChoosePackage.setOnClickListener {
            if (binding.layoutPackage.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    binding.cardviewChoosePackage,
                    AutoTransition()
                )
                binding.layoutPackage.visibility = View.VISIBLE
                rotationAngle = if (rotationAngle == 0) 180 else 0 //toggle
                binding.imgDropDown.animate().rotation(rotationAngle.toFloat()).setDuration(500)
                    .start()
            } else {
                hideContestLayout();
            }
        }
        binding.cardviewPaymentOption.setOnClickListener {
            if (binding.layoutPaymentOption.visibility == View.GONE) {
                setPaymentLayoutVissible()
            } else {
                binding.layoutPaymentOption.visibility = View.GONE
                rotationAnglePayment = if (rotationAnglePayment == 0) 180 else 0 //toggle
                binding.imgDropDownOption.animate().rotation(rotationAnglePayment.toFloat())
                    .setDuration(500).start()
                TransitionManager.beginDelayedTransition(
                    binding.cardviewPaymentOption,
                    AutoTransition()
                )
                val layoutParams =
                    binding.cardviewPaymentOption.getLayoutParams() as LinearLayout.LayoutParams
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                binding.cardviewPaymentOption.setLayoutParams(layoutParams)
            }
        }
        binding.layoutPackage.btnNext.setOnClickListener {
            CardFragment(price)
            setPaymentLayoutVissible()
            hideContestLayout()
        }
        return binding.root
    }

    fun callBack(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_paymentModelFragment_to_razorPayViewFragment,
            bundle)
    }


    fun getAmount(): String? {
        return price
    }

    private fun setselection(btn: View) {
        binding.layoutPackage.btn3000Rs.setBackgroundDrawable(resources.getDrawable(R.drawable.back_solid_contest))
        binding.layoutPackage.btn1000Rs.setBackgroundDrawable(resources.getDrawable(R.drawable.back_solid_contest))
        binding.layoutPackage.btn2000Rs.setBackgroundDrawable(resources.getDrawable(R.drawable.back_solid_contest))
        binding.layoutPackage.btn5000.setBackgroundDrawable(resources.getDrawable(R.drawable.back_solid_contest))
        btn.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_solid_contest))
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }

    private fun setPaymentLayoutVissible() {
        binding.layoutPaymentOption.visibility = View.VISIBLE

        val layoutParams =
            binding.cardviewPaymentOption.getLayoutParams() as LinearLayout.LayoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        binding.cardviewPaymentOption.setLayoutParams(layoutParams)
        rotationAnglePayment = if (rotationAnglePayment == 0) 180 else 0 //toggle
        binding.imgDropDownOption.animate().rotation(rotationAnglePayment.toFloat())
            .setDuration(500).start()
        TransitionManager.beginDelayedTransition(
            binding.cardviewPaymentOption,
            AutoTransition()
        )
    }

    private fun hideContestLayout() {
        TransitionManager.beginDelayedTransition(
            binding.cardviewChoosePackage,
            AutoTransition()
        )
        binding.layoutPackage.visibility = View.GONE
        rotationAngle = if (rotationAngle == 0) 180 else 0 //toggle
        binding.imgDropDown.animate().rotation(rotationAngle.toFloat()).setDuration(500)
            .start()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        initTabLayout()

    }

    private fun initTabLayout() {
        val headerView =
            (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.custom_payment_tab, null, false)

        val linearLayoutOne = headerView.findViewById(R.id.ll_credit_card) as LinearLayout
        val linearLayout2 = headerView.findViewById(R.id.ll_debit) as LinearLayout
        val linearLayout3 = headerView.findViewById(R.id.ll_net_banking) as LinearLayout
        val linearLayout4 = headerView.findViewById(R.id.ll_wallet) as LinearLayout

        binding?.layoutPaymentOption.tablayout?.getTabAt(0)?.customView = linearLayoutOne
        binding?.layoutPaymentOption.tablayout?.getTabAt(1)?.customView = linearLayout2
        binding?.layoutPaymentOption.tablayout?.getTabAt(2)?.customView = linearLayout3
        binding?.layoutPaymentOption.tablayout?.getTabAt(3)?.customView = linearLayout4
        (binding?.layoutPaymentOption.tablayout?.getTabAt(0)?.customView as LinearLayout).setBackgroundDrawable(resources.getDrawable(R.drawable.selected_solid_contest))
        binding?.layoutPaymentOption.tablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                ( tab.customView as LinearLayout).setBackgroundDrawable(resources.getDrawable(R.drawable.selected_solid_contest))

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                ( tab.customView as LinearLayout).setBackgroundDrawable(resources.getDrawable(R.drawable.back_solid_contest))
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                ( tab.customView as LinearLayout).setBackgroundDrawable(resources.getDrawable(R.drawable.selected_solid_contest))
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1101) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val amount =
                        it.getParcelableExtra<PaymentDetailsModel>(Constants.PAYMENT_MODEL)?.amount
                    val details = it.getStringExtra(Constants.PRODUCT).toString()
//                    val amount = it.getStringExtra("amount").toString()
                    val razorId = it.getStringExtra(Constants.RAZOR_PAY_ID).toString()
                    Toast.makeText(
                        requireContext(),
                        "Payment Success $details $amount $razorId",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                data?.let {
                    val error = it.getStringExtra(Constants.ERROR_CODE)
                    val message = it.getStringExtra(Constants.ERROR_MESSAGE)
                    Toast.makeText(
                        requireContext(),
                        "Error $error  Message $message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun setupViewPager() {
        binding.layoutPaymentOption.tablayout
        binding.layoutPaymentOption.tablayout.setupWithViewPager(binding?.layoutPaymentOption.viewpager)
        adapter =
            ViewPagerAdapter(childFragmentManager)

        binding?.layoutPaymentOption.viewpager?.offscreenPageLimit = 4
        adapter?.addFragment(CardFragment(price), "ONE")
        adapter?.addFragment(CardFragment(price), "TWO")
        adapter?.addFragment(NetBankingFragment(), "THREE")
        adapter?.addFragment(CardFragment(price), "FOUR")
        binding?.layoutPaymentOption.viewpager?.adapter = adapter

    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    private fun isValidEmail(strEmail: String?): Boolean {
        return strEmail != null && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        val pattern =
            Pattern.compile(Constants.PHONE_PATTERN)
        val matcher = pattern.matcher(phone)
        return matcher.matches()
    }

}
