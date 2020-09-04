package com.ballchalu.ui.winners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentWinnersBinding
import com.ballchalu.ui.winners.adapter.WinnersAdapter
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.util.ConstantsBase
import com.ballchalu.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [WinnersFragment] subclass.
 */
class WinnersFragment : BaseFragment() {
    private lateinit var binding: FragmentWinnersBinding
    private var winnAdapter: WinnersAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WinnersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentWinnersBinding.inflate(inflater, container, false).apply {


        }
        arguments?.let {
            viewModel.matchId = it.getInt(ConstantsBase.KEY_MATCH_ID)
            viewModel.contestId = it.getInt(ConstantsBase.KEY_CONTESTS_MATCH_ID)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        viewModel.winnerListObserver.observe(viewLifecycleOwner, EventObserver {
            if (it != null) {
                winnAdapter?.setItemList(it)
            }
        })
        viewModel.calWinnerAsync()
    }

    private fun initAdapter() {
        winnAdapter = WinnersAdapter()
        binding.rvWinners.adapter = winnAdapter
    }

}
