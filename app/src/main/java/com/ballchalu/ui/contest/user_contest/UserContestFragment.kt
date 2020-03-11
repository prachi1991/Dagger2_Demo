package com.ballchalu.ui.contest.user_contest

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
import com.ballchalu.ui.contest.ContestViewModel
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ballchalu.ui.dialog.NotificationDialog
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.domain.contest.UserContest
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class UserContestFragment : BaseFragment() {
    private var contestAdapter: ContestAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel

    private var myContestList: ArrayList<Contest>? = null
    private var userContestList: ArrayList<UserContest>? = arrayListOf()

    lateinit var notificationDialog: NotificationDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentContestBinding.inflate(inflater).apply {
            lifecycleOwner = this@UserContestFragment

            myContestList = arrayListOf()
        }
        arguments?.let {
            viewModel.matchItem = it.getSerializable(ConstantsBase.KEY_MATCH_ITEM) as MatchListing?
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter() //match id

        viewModel.matchUserContestResult.observe(viewLifecycleOwner, EventObserver {
            contestAdapter?.clear()
            myContestList?.clear()
            userContestList = it.contests as ArrayList<UserContest>?
            userContestList?.forEach {
                myContestList?.add(it.contest!!)
            }
            contestAdapter?.setItemList(myContestList, true)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.createContestResult.observe(viewLifecycleOwner, EventObserver {
            //   notificationDialog.showMesssage(true,"You Successfully By Contest")
            Toast.makeText(context, "You Successfully By Contest", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserMatchesContest()
    }

    private fun initSessionAdapterAdapter() {
        contestAdapter = ContestAdapter(object : ContestAdapter.OnItemClickListener {
            override fun onBuyNowClicked(contestModel: Contest) {

            }

            override fun onPlayNowClicked(contestModel: Contest) {
                val bundle = Bundle().apply {
                    putInt(ConstantsBase.KEY_PROVIDER_ID, contestModel.match.providerId ?: 0)
                    putInt(ConstantsBase.KEY_PROVIDER_ID, contestModel.match.providerId ?: 0)
                }
                findNavController().navigate(R.id.nav_home_match_details, bundle)
            }
        })
        binding.rvContest.adapter = contestAdapter
    }

}
