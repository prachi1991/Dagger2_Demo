package com.ballchalu.ui.create_bet

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.databinding.FragmentCreateBetBinding
import com.ballchalu.ui.match_listing.adapter.InPlayMatchListingAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.domain.create_bet.*
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

class CreateBetFragment : DaggerAppCompatDialogFragment(),
    InPlayMatchListingAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CreateBetViewModel

    private lateinit var binding: FragmentCreateBetBinding
    var count = 0

    private var isCountMultiply = true
    internal var listener: OnBetResponseSuccessListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentCreateBetBinding.inflate(inflater, container, false).apply {

            lifecycleOwner = this@CreateBetFragment

            ivMinus.setOnClickListener {
                if (count > 0)
                    count--
                updateCountText()
            }

            ivPlus.setOnClickListener {
                if (count < 10)
                    count++
                updateCountText()
            }

            btnClearBet.setOnClickListener {
                tvCount.setText("0")
                count = 0
            }

            btnPlaceBet.setOnClickListener {
                viewModel.placeBet(tvCount.text.toString())
                hideKeyboard()
            }

            imgClose.setOnClickListener {
                dialog?.dismiss()
            }



            tvCount.doOnTextChanged { text, start, count, after ->
                if (!text.isNullOrEmpty())
                    updateRateCount()
                else
                    tvReturnRate.text = "0"
            }

        }
        arguments?.let { bundle ->
            val betDetailsBundle =
                bundle.getParcelable<BetDetailsBundle>(ConstantsBase.KEY_CREATE_BET_REQ)
            betDetailsBundle?.betReq?.let {
                viewModel.createBetReq = it
            }
            betDetailsBundle?.betSessionReq?.let {
                viewModel.createSessionBetReq = it
            }
            updateUI(viewModel.createBetReq, viewModel.createSessionBetReq)
            isCountMultiply = betDetailsBundle?.isCountMultiply == true
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMatchesListing()
    }


    private fun updateCountText() {
        val list = viewModel.betArray()
        if (count in 0..10)
            binding.tvCount.setText(list[count].toString())
    }

    private fun updateRateCount() {
        val count = if (isCountMultiply) binding.tvOddValue.text.toString().toDouble() else 1.0

        binding.tvReturnRate.text = String.format(
            "%.2f",
            (count * binding.tvCount.text.toString().toDouble())
        )
    }

    private fun updateUI(createBetReq: CreateBetReq?, createSessionBetReq: CreateSessionBetReq?) {

        binding.tvOddValue.text = createBetReq?.oddsVal ?: createSessionBetReq?.oddValue ?: ""
        createBetReq?.oddsType?.let {
            binding.tvLagaiKhaiLabel.text = it
            binding.tvLagaiKhaiLabel.visibility = VISIBLE

        }
        binding.tvMarketType.text =
            createBetReq?.heroicMarketType ?: createSessionBetReq?.heroicMarketType ?: ""
        binding.tvTitle.text =
            createBetReq?.evenTypeTitle ?: createSessionBetReq?.evenTypeTitle ?: ""
    }

    private fun getMatchesListing() {
//        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.IN_PLAY)
//        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.UPCOMING)
//
//        binding.llInplay.visibility = View.GONE
//        binding.llUpcoming.visibility = View.GONE
//
//
//        viewModel.inPlayListEvent.observe(viewLifecycleOwner, EventObserver {
//
//            if (it.matches?.size != 0)
//                binding.llInplay.visibility = View.VISIBLE
//
//            inPlayListAdapter = InPlayBetMatchListAdapter()
//            binding.rvInPlayMatchListing.adapter = inPlayListAdapter
//            inPlayListAdapter?.setItemList(it.matches, ConstantsBase.IN_PLAY)
//        })
//
//        viewModel.upcomingMatchListEvent.observe(viewLifecycleOwner, EventObserver {
//            if (it.matches?.size != 0)
//                binding.llUpcoming.visibility = View.VISIBLE
//            upcomingListAdapter = InPlayMatchListingAdapter(this)
//            binding.rvUpcomingMatchListing.adapter = upcomingListAdapter
//            upcomingListAdapter?.setItemList(it.matches, ConstantsBase.UPCOMING)
//        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()

        })

        viewModel.createBetObserver.observe(viewLifecycleOwner, EventObserver {
            hideKeyboard()
            listener?.onBetSuccess(it)
            if (it.status.equals(ConstantsBase.SUCCESS, true))
                dialog?.dismiss()
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        })

        viewModel.createSessionBetObserver.observe(viewLifecycleOwner, EventObserver {
            listener?.onSessionBetSuccess(it)
            if (it.status.equals(ConstantsBase.SUCCESS, true))
                dialog?.dismiss()
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })


    }

    override fun onMatchClicked(matchListingItem: MatchListingItem) {
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    interface OnBetResponseSuccessListener {
        fun onBetSuccess(createBetRes: CreateBetRes)
        fun onSessionBetSuccess(createSessionBetRes: CreateSessionBetRes?)
    }

    private fun hideKeyboard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(dialog?.currentFocus?.windowToken, 0)
    }
}
