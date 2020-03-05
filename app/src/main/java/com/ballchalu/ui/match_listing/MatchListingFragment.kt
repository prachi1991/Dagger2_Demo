package com.ballchalu.ui.match_listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchListingBinding
import com.ballchalu.ui.match_listing.adapter.InPlayMatchListingAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListingReq
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class MatchListingFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMatchListingBinding
    private lateinit var viewModel: MatchListingViewModel

    private var inPlayMatchListingAdapter: InPlayMatchListingAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentMatchListingBinding.inflate(inflater).apply {
            lifecycleOwner = this@MatchListingFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMatchesListing()
    }

    private fun getMatchesListing() {

        inPlayMatchListingAdapter = InPlayMatchListingAdapter()
        binding.rvMatchListing.adapter = inPlayMatchListingAdapter

        viewModel.callMatchListing("cricket","inplay")
        viewModel.matchListingCall.observe(viewLifecycleOwner,EventObserver{
            println("matchListing_size: "+it.matches?.size)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {

        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            println("matchListing_error: $it")
        })


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MatchListingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
