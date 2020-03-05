package com.ballchalu.ui.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentContestBinding
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ballchalu.ui.dialog.NotificationDialog
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.domain.contest.UserContest
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class ContestFragment : BaseFragment() {
    private var contestAdapter: ContestAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentContestBinding
    private lateinit var viewModel: ContestViewModel

    private var myContestList: ArrayList<Contest>? = arrayListOf()
    private var userContestList: ArrayList<UserContest>? = arrayListOf()
    private var allContestList: ArrayList<Contest>? = arrayListOf()

    lateinit var notificationDialog: NotificationDialog

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
       // notificationDialog = activity!!.let { NotificationDialog(it) }

        viewModel.getAllMatchesContest("2")

        viewModel.matchContestResult.observe(viewLifecycleOwner,EventObserver{
            allContestList = it.contests as ArrayList<Contest>?
            contestAdapter?.setItemList(allContestList)
        })

        viewModel.matchUserContestResult.observe(viewLifecycleOwner,EventObserver{
            userContestList = it.contests as ArrayList<UserContest>?
            userContestList?.forEach {
                myContestList?.add(it.contest!!)
            }
            contestAdapter?.setItemList(myContestList,true)
        })

        viewModel.loading.observe(viewLifecycleOwner,EventObserver{
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            radioButtonClickable(!it)
        })

        viewModel.createContestResult.observe(viewLifecycleOwner,EventObserver{
         //   notificationDialog.showMesssage(true,"You Successfully By Contest")
            Toast.makeText(context,"You Successfully By Contest",Toast.LENGTH_SHORT).show()
        })


        binding.rbGroup.setOnCheckedChangeListener { group, checkedId ->
            run {
                when (checkedId) {
                    R.id.rbAllContest -> {
                        contestAdapter?.clear()
                        viewModel.getAllMatchesContest("2")
                    }
                    R.id.rbMyContest -> {
                        contestAdapter?.clear()
                        viewModel.getUserMatchesContest("2")
                    }
                    else -> false
                }
            }
        }
        contestAdapter?.setItemList(allContestList)
    }

    private fun initSessionAdapterAdapter() {
        contestAdapter = ContestAdapter(object : ContestAdapter.OnItemClickListener{
            override fun onClick(contestModel: Contest?) {
                viewModel.createUserMatchContest(contestModel?.id.toString())
            }
        })
        binding.rvContest.adapter = contestAdapter
    }

    private fun radioButtonClickable(it:Boolean)
    {
        binding.rbAllContest.isEnabled = it
        binding.rbMyContest.isEnabled = it

        binding.rbAllContest.isClickable = it
        binding.rbMyContest.isClickable = it
    }

}
