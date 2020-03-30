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
import com.ballchalu.ui.contest.ContestCountListener
import com.ballchalu.ui.contest.ContestViewModel
import com.ballchalu.ui.contest.user_contest.adapter.UserContestAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.domain.contest.UserContest
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class UserContestFragment : BaseFragment() {
    private var listener: ContestCountListener? = null
    private var contestAdapter: UserContestAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel

    private var userContestList: ArrayList<UserContest>? = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentContestBinding.inflate(inflater).apply {
            lifecycleOwner = this@UserContestFragment

        }
        arguments?.let {
            viewModel.matchItem = it.getSerializable(ConstantsBase.KEY_MATCH_ITEM) as MatchListing?
            viewModel.isDeclared = it.getBoolean(ConstantsBase.KEY_DECLARED, false)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter() //match id

        viewModel.matchUserContestResult.observe(viewLifecycleOwner, EventObserver {
            contestAdapter?.clear()
            it.contests?.size?.let { it1 -> listener?.onUserContest(it1) }
            userContestList = it.contests as ArrayList<UserContest>?
            contestAdapter?.setItemList(userContestList, true)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.createContestResult.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, "You Successfully Buy Contest", Toast.LENGTH_SHORT).show()
        })
        viewModel.getUserMatchesContest()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserMatchesContest()
    }

    private fun initSessionAdapterAdapter() {
        contestAdapter = UserContestAdapter(object : UserContestAdapter.OnItemClickListener {
            override fun onPlayNowClicked(contestModel: UserContest) {
                val bundle = Bundle().apply {
                    putInt(
                        ConstantsBase.KEY_PROVIDER_ID,
                        contestModel.contest?.match?.providerId ?: 0
                    )
                    putInt(ConstantsBase.KEY_CONTESTS_ID, contestModel.id ?: 0)
                    putString(ConstantsBase.KEY_TITLE, contestModel.contest?.title)
                    putInt(ConstantsBase.KEY_CONTESTS_MATCH_ID, contestModel.contest?.id ?: 0)
                    putInt(ConstantsBase.KEY_MATCH_ID, contestModel.contest?.match?.id ?: 0)
                    putBoolean(ConstantsBase.KEY_DECLARED, viewModel.isDeclared)
                }
                findNavController().navigate(R.id.nav_home_match_details, bundle)
            }
        }, viewModel.isDeclared)
        binding.rvContest.adapter = contestAdapter
    }

    fun setListeners(onCountListener: ContestCountListener) {
        this.listener = onCountListener
    }

}
