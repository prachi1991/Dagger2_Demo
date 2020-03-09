package com.ballchalu.ui.create_bet

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentCreateBetBinding
import com.ballchalu.ui.create_bet.adapter.InPlayBetMatchListAdapter
import com.ballchalu.ui.match_listing.adapter.InPlayMatchListingAdapter
import com.ballchalu.utils.DecimalDigitsInputFilter
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
                if(count<10)
                    count ++
                updateCountText()
            }

            btnClear.setOnClickListener {
                tvCount.setText("0")
            }

            tvCount.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                   count = 0
                } else {

                }
            }

            tvCount.doOnTextChanged { text, start, count, after ->
                if(!text.isNullOrEmpty())
                updateRateCount()
                else
                    tvReturnRate.setText("0")
            }

        }
        return binding.root
    }

    fun updateCountText()
    {
        binding.tvCount.clearFocus()
        val list = viewModel.betArray()
        if(count in 1..10)
        binding.tvCount.setText(list[count].toString())
    }

    fun updateRateCount()
    {
        binding.tvReturnRate.text = String.format("%.2f",(binding.tvCount.text.toString().toDouble() * binding.bettingRate.text.toString().toDouble()))
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
