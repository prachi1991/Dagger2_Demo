package com.ballchalu.ui.contest.all_contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentContestBinding
import com.ballchalu.ui.contest.ContestViewModel
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class ContestFragment : BaseFragment() {
    private var contestAdapter: ContestAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel

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

        viewModel.matchContestResult.observe(viewLifecycleOwner, EventObserver { it ->
            contestAdapter?.clear()

            if (viewModel.isDeclared)
                contestAdapter?.setItemList(it.contests)
            else {
                allContestList?.clear()
                it.contests?.forEach {
                    if (it.availableSpots ?: 0 > 0)
                        allContestList?.add(it)
                }
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
        }, viewModel.isDeclared)
        binding.rvContest.adapter = contestAdapter
    }

}
