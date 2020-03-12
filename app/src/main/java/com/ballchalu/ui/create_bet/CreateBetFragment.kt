package com.ballchalu.ui.create_bet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.databinding.FragmentCreateBetBinding
import com.ballchalu.ui.create_bet.adapter.InPlayBetMatchListAdapter
import com.ballchalu.ui.match_listing.adapter.InPlayMatchListingAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.domain.create_bet.CreateBetReq
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
    private var inPlayListAdapter: InPlayBetMatchListAdapter? = null
    private var upcomingListAdapter: InPlayMatchListingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentCreateBetBinding.inflate(inflater, container, false).apply {

            lifecycleOwner = this@CreateBetFragment

            minusLayout.setOnClickListener {
                if (count > 0)
                    count--
                updateCountText()
            }

            plusLayout.setOnClickListener {
                if (count < 10)
                    count++
                updateCountText()
            }

            btnClearBet.setOnClickListener {
                tvCount.setText("0")
                count = 0
            }

            btnPlaceBet.setOnClickListener {
                if (tvCount.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Please enter the amount", Toast.LENGTH_SHORT).show()
                } else {
                    if (tvCount.text.toString().toInt() < 1) {
                        Toast.makeText(
                            context,
                            "Amount should be grater than zero",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.callCreateBet(tvCount.text.toString())
                    }
                }
            }

            imgClose.setOnClickListener {
                dismiss()
            }


            tvCount.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    count = 0
                } else {

                }
            }

            tvCount.doOnTextChanged { text, start, count, after ->
                if (!text.isNullOrEmpty())
                    updateRateCount()
                else
                    tvReturnRate.setText("0")
            }

        }
        arguments?.let {
            viewModel.createBetReq = it.getParcelable(ConstantsBase.KEY_CREATE_BET_REQ)
            updateUI(viewModel.createBetReq)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMatchesListing()
    }


    private fun updateCountText() {
        binding.tvCount.clearFocus()
        val list = viewModel.betArray()
        if (count in 1..10)
            binding.tvCount.setText(list[count].toString())
    }

    private fun updateRateCount() {
        binding.tvReturnRate.text = String.format(
            "%.2f",
            (binding.tvCount.text.toString().toDouble() * binding.tvOddValue.text.toString().toDouble())
        )
    }

    private fun updateUI(createBetReq: CreateBetReq?) {

        binding.tvOddValue.text = createBetReq?.oddsVal
        binding.tvLagaiKhaiLabel.text = createBetReq?.oddsType
        binding.tvMarketType.text = createBetReq?.heroicMarketType
        binding.tvTitle.text = createBetReq?.evenTypeTitle
    }

    private fun getMatchesListing() {
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.IN_PLAY)
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.UPCOMING)

        binding.llInplay.visibility = View.GONE
        binding.llUpcoming.visibility = View.GONE


        viewModel.inPlayListEvent.observe(viewLifecycleOwner, EventObserver {

            if (it.matches?.size != 0)
                binding.llInplay.visibility = View.VISIBLE

            inPlayListAdapter = InPlayBetMatchListAdapter()
            binding.rvInPlayMatchListing.adapter = inPlayListAdapter
            inPlayListAdapter?.setItemList(it.matches, ConstantsBase.IN_PLAY)
        })

        viewModel.upcomingMatchListEvent.observe(viewLifecycleOwner, EventObserver {
            if (it.matches?.size != 0)
                binding.llUpcoming.visibility = View.VISIBLE
            upcomingListAdapter = InPlayMatchListingAdapter(this)
            binding.rvUpcomingMatchListing.adapter = upcomingListAdapter
            upcomingListAdapter?.setItemList(it.matches, ConstantsBase.UPCOMING)
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()

        })

        viewModel.createBetObserver.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        })

        viewModel.createSessionBetObserver.observe(viewLifecycleOwner, EventObserver {
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
        );
    }

}
