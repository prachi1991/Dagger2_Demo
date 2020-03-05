package com.ballchalu.ui.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentContestBinding
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class ContestFragment : BaseFragment() {
    private var contestAdapter: ContestAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel

    private var myContestList: ArrayList<Contest> = arrayListOf()
    private var allContestList: ArrayList<Contest>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentContestBinding.inflate(inflater).apply {
            lifecycleOwner = this@ContestFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter()
     //   myContestList.add("Play")


        viewModel.callSignUp("8")

        viewModel.matchContestResult.observe(viewLifecycleOwner,EventObserver{
            allContestList = it.contests as ArrayList<Contest>?
            contestAdapter?.setItemList(allContestList)
        })


        binding.rbGroup.setOnCheckedChangeListener { group, checkedId ->
            run {
                when (checkedId) {
                    R.id.rbAllContest -> contestAdapter?.setItemList(allContestList)
                    R.id.rbMyContest -> contestAdapter?.setItemList(myContestList)
                    else -> false
                }
            }
        }
        contestAdapter?.setItemList(allContestList)
    }

    private fun initSessionAdapterAdapter() {
        contestAdapter = ContestAdapter()
        binding.rvContest.adapter = contestAdapter
    }

}
