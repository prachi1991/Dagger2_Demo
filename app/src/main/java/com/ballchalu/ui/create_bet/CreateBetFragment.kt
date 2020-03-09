package com.ballchalu.ui.create_bet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentCreateBetBinding
import com.ballchalu.databinding.FragmentCreateBetBindingImpl
import com.ballchalu.ui.create_bet.adapter.InPlayBetMatchListAdapter
import com.ballchalu.ui.match_listing.MatchListingViewModel
import com.ballchalu.ui.match_listing.adapter.InPlayMatchListingAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class CreateBetFragment : BaseFragment(), InPlayMatchListingAdapter.OnItemClickListener {

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
                if(count>0)
                count --
                updateCountText()
            }

            plusLayout.setOnClickListener {
                if(count>=0)
                    count ++
                updateCountText()
            }

        }
        return binding.root
    }

    fun updateCountText()
    {
        binding.tvCount.text = count.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMatchesListing()
    }

    private fun getMatchesListing() {
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.IN_PLAY)
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.UPCOMING)

        binding.llInplay.visibility=View.GONE
        binding.llUpcoming.visibility=View.GONE


        viewModel.inPlayListEvent.observe(viewLifecycleOwner, EventObserver{

            if(it.matches?.size!=0)
                binding.llInplay.visibility=View.VISIBLE

            inPlayListAdapter = InPlayBetMatchListAdapter()
            binding.rvInPlayMatchListing.adapter = inPlayListAdapter
            inPlayListAdapter?.setItemList(it.matches, ConstantsBase.IN_PLAY)
        })

        viewModel.upcomingListEvent.observe(viewLifecycleOwner, EventObserver{
            if(it.matches?.size!=0)
                binding.llUpcoming.visibility=View.VISIBLE
            upcomingListAdapter = InPlayMatchListingAdapter(this)
            binding.rvUpcomingMatchListing.adapter = upcomingListAdapter
            upcomingListAdapter?.setItemList(it.matches, ConstantsBase.UPCOMING)
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()

        })


    }

    override fun onMatchClicked(matchListingItem: MatchListingItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
