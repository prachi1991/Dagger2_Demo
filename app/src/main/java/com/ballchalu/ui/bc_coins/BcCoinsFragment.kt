package com.ballchalu.ui.bc_coins

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentBcCoinsBinding
import com.ballchalu.databinding.FragmentContestBinding
import com.ballchalu.ui.bc_coins.adapter.BcCoinAdapter
import com.ballchalu.ui.contest.ContestViewModel
import com.ballchalu.ui.contest.adapter.ContestAdapter
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class BcCoinsFragment : BaseFragment() {

    private var bcCoinAdapter: BcCoinAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBcCoinsBinding
    private lateinit var viewModel: BcCoinsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentBcCoinsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@BcCoinsFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter()
    }

    private fun initSessionAdapterAdapter() {
        bcCoinAdapter = BcCoinAdapter()
        binding.rvBcCoin.adapter = bcCoinAdapter
    }

}
