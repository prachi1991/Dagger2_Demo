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

        initSessionAdapterAdapter()
    }

    private fun initSessionAdapterAdapter() {
        inPlayMatchListingAdapter = InPlayMatchListingAdapter()
        binding.rvMatchListing.adapter = inPlayMatchListingAdapter
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
