package com.ballchalu.ui.contest.all_contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentContestBinding
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.domain.MatchListing
import com.ballchalu.shared.domain.contest.Contest
import com.ballchalu.shared.util.ConstantsBase
import com.ballchalu.shared.util.activityViewModelProvider
import com.ballchalu.shared.util.viewModelProvider
import com.ballchalu.ui.contest.ContestViewModel
import com.ballchalu.ui.contest.MainContestFragment
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ballchalu.ui.navigation.NavigationViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber
import javax.inject.Inject

class ContestFragment : BaseFragment() {
    private var contestAdapter: ContestAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel
    private lateinit var model: NavigationViewModel

    private var allContestList: ArrayList<Contest>? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)


        binding = FragmentContestBinding.inflate(inflater).apply {
            lifecycleOwner = this@ContestFragment
            allContestList = arrayListOf()
        }

        arguments?.let {
            viewModel.matchItem = it.getSerializable(ConstantsBase.KEY_MATCH_ITEM) as MatchListing?
            viewModel.isDeclared = it.getBoolean(ConstantsBase.KEY_DECLARED, false)
        }

        /*binding.imgClose.setOnClickListener {
            binding.bottomSheetBuy.visibility = View.GONE
        }
        binding.btnAddCoin.setOnClickListener {

        }*/

        return binding.root
    }

    private fun expandBottomSheet() {

        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSessionAdapterAdapter()
        bottomSheetBehavior = BottomSheetBehavior.from(binding.llBottomsheet)
        hideBottomSheet()
        //  binding.bottomSheetBuy.visibility = View.VISIBLE
        viewModel.createContestResult.observe(viewLifecycleOwner, EventObserver { it ->
            model = activityViewModelProvider(viewModelFactory)
            model.userDetails.postValue(Event(it.userContest?.user))


            Toast.makeText(context, "You Successfully Buy Contest", Toast.LENGTH_SHORT).show()
            viewModel.getAllMatchesContest()


        })
        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver { it ->
            viewModel.availablecoins = it?.bc_coins.toString()
            expandBottomSheet()
            setDataToBottomSheet(viewModel.contestModel, it?.bc_coins.toString())
            isAbletoBuyContest()

        })
        viewModel.matchContestResult.observe(viewLifecycleOwner, EventObserver { it ->
            contestAdapter?.clear()
            it.contests
            it.contests?.count { it.isParticipated }?.let { count ->
                onUserContest(count)
            }
            if (viewModel.isDeclared) {
                it.contests?.size?.let { it1 -> onAllContest(it1) }
                contestAdapter?.setItemList(it.contests)
            } else {
                allContestList?.clear()
                it.contests?.forEach {
                    if (it.availableSpots ?: 0 > 0)
                        allContestList?.add(it)
                }
                allContestList?.size?.let { it1 -> onAllContest(it1) }
                contestAdapter?.setItemList(allContestList)
            }

        })
        binding.btnAddCoin.setOnClickListener {
            hideBottomSheet();
            setButtonClick()


        }
        binding.llClose.setOnClickListener {
            hideBottomSheet();
        }
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

    private fun setButtonClick() {
        when (binding.btnAddCoin.text.toString()) {
            getString(R.string.buy_now) -> {
                viewModel.createUserMatchContest(viewModel.contestModel.id.toString())
            }
            getString(R.string.add_coins) -> {
                findNavController().navigate(R.id.paymentSelectionActivity)
            }
        }
    }

    fun setDataToBottomSheet(
        contest: Contest,
        availcoins: String
    ) {
        binding.tvContestCoinsValue.text = contest.getEntery()
        binding.tvAvailableCoinsValue.text = availcoins
    }

    private fun isAbletoBuyContest() {
        val contestPrice = binding.tvContestCoinsValue.text.toString().replace(Regex("/-"), ".0")
        val availablecoins = binding.tvAvailableCoinsValue.text.toString()
        if (availablecoins.toDouble().compareTo(contestPrice.toDouble()) >=0)
            binding.btnAddCoin.setText(getString(R.string.buy_now))
        else binding.btnAddCoin.setText(getString(R.string.add_coins))
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private fun onUserContest(count: Int) {
        if (parentFragment is MainContestFragment)
            (parentFragment as MainContestFragment).onUserContest(count)
    }

    private fun onAllContest(count: Int) {
        if (parentFragment is MainContestFragment)
            (parentFragment as MainContestFragment).onAllContest(count)
    }

    override fun onResume() {
        viewModel.getAllMatchesContest()
        super.onResume()
    }


    private fun initSessionAdapterAdapter() {
        contestAdapter = ContestAdapter(object : ContestAdapter.OnItemClickListener {
            override fun onBuyNowClicked(contestModel: Contest) {

                viewModel.createUserMatchContest(contestModel.id.toString())
            }

            override fun onPlayNowClicked(contestModel: Contest) {

            }

            override fun onResultClicked(contestModel: Contest) {
                if (viewModel.isDeclared)
                    openMatchDetailsScreen(contestModel)
            }

            override fun onClick(contestModel: Contest) {
                if (!contestModel.isParticipated) {
                    viewModel.contestModel = contestModel
                    viewModel.callUserDetails()
                }


            }
        }, viewModel.isDeclared)
        binding.rvContest.adapter = contestAdapter
    }

    private fun openMatchDetailsScreen(contestModel: Contest) {
        val bundle = Bundle().apply {
            putInt(
                ConstantsBase.KEY_PROVIDER_ID,
                contestModel.match.providerId ?: 0
            )
            val liveUser = contestModel.spots?.minus(
                contestModel.availableSpots ?: 1
            ) ?: 1
            putInt(ConstantsBase.KEY_LIVE_USER, liveUser)
            putInt(ConstantsBase.KEY_CONTESTS_ID, contestModel.id ?: 0)
            putString(ConstantsBase.KEY_TITLE, contestModel.title)
            putInt(ConstantsBase.KEY_CONTESTS_MATCH_ID, contestModel.id ?: 0)
            putInt(ConstantsBase.KEY_MATCH_ID, contestModel.match.id ?: 0)
            putBoolean(ConstantsBase.KEY_DECLARED, viewModel.isDeclared)
        }
        findNavController().navigate(R.id.nav_home_match_details, bundle)
    }
}
