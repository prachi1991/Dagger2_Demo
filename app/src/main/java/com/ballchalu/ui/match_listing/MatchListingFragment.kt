package com.ballchalu.ui.match_listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R

import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchListingBinding
import com.ballchalu.ui.match_listing.adapter.InPlayMatchListingAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class MatchListingFragment : BaseFragment(), InPlayMatchListingAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMatchListingBinding
    private lateinit var viewModel: MatchListingViewModel

    private var inPlayListAdapter: InPlayMatchListingAdapter? = null
    private var upcomingListAdapter: InPlayMatchListingAdapter? = null


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pullRefreshLayout.setOnRefreshListener {
            getMatchesListing()
        }
        getMatchesListing()
    }

    private fun getMatchesListing() {
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.IN_PLAY)
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.UPCOMING)

        viewModel.inPlayListEvent.observe(viewLifecycleOwner, EventObserver {

            if (it.matches?.size != 0)
                binding.llInplay.visibility = View.VISIBLE

            inPlayListAdapter = InPlayMatchListingAdapter(this)
            binding.rvInPlayMatchListing.adapter = inPlayListAdapter
            inPlayListAdapter?.setItemList(it.matches, ConstantsBase.IN_PLAY)
        })
        viewModel.upcomingListEvent.observe(viewLifecycleOwner, EventObserver {
            if (it.matches?.size != 0)
                binding.llUpcoming.visibility = View.VISIBLE
            upcomingListAdapter = InPlayMatchListingAdapter(this)
            binding.rvUpcomingMatchListing.adapter = upcomingListAdapter
            upcomingListAdapter?.setItemList(it.matches, ConstantsBase.UPCOMING)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            if (!it) binding.pullRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()

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

    override fun onMatchClicked(matchListingItem: MatchListingItem, isMatchStarted: Boolean) {
        val bundle = Bundle().apply {
            putSerializable(ConstantsBase.KEY_MATCH_ITEM, matchListingItem.match)
            putBoolean(ConstantsBase.KEY_IS_MATCH_STARTED, isMatchStarted)
        }
        findNavController().navigate(R.id.nav_contest, bundle)
    }
}
