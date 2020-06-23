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
import com.ballchalu.ui.contest.ContestCountListener
import com.ballchalu.ui.contest.ContestViewModel
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ballchalu.ui.navigation.NavigationViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.activityViewModelProvider
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class ContestFragment : BaseFragment() {
    private var listener: ContestCountListener? = null
    private var contestAdapter: ContestAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel
    private lateinit var model: NavigationViewModel

    private var allContestList: ArrayList<Contest>? = null

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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter()

        viewModel.createContestResult.observe(viewLifecycleOwner, EventObserver { it ->
            model = activityViewModelProvider(viewModelFactory)
            model.userDetails.postValue(Event(it.userContest?.user))
            Toast.makeText(context, "You Successfully Buy Contest", Toast.LENGTH_SHORT).show()
            viewModel.getAllMatchesContest()
        })
        viewModel.matchContestResult.observe(viewLifecycleOwner, EventObserver { it ->
            contestAdapter?.clear()
            it.contests?.count { it.isParticipated }?.let { count ->
                listener?.onUserContest(count)
            }
            if (viewModel.isDeclared) {
                it.contests?.size?.let { it1 -> listener?.onAllContest(it1) }
                contestAdapter?.setItemList(it.contests)
            } else {
                allContestList?.clear()
                it.contests?.forEach {
                    if (it.availableSpots ?: 0 > 0)
                        allContestList?.add(it)
                }
                allContestList?.size?.let { it1 -> listener?.onAllContest(it1) }
                contestAdapter?.setItemList(allContestList)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

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
        }, viewModel.isDeclared)
        binding.rvContest.adapter = contestAdapter
    }

    fun setListeners(onCountListener: ContestCountListener) {
        this.listener = onCountListener
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
